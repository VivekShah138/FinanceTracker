package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.room.modules.currency_rates.CurrencyRatesDao
import com.example.financetracker.data.data_source.local.room.modules.userprofile.UserProfileDao
import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
import com.example.financetracker.data.data_source.remote.CurrencyRatesApi
import com.example.financetracker.mapper.CountryMapper
import com.example.financetracker.mapper.CurrencyRatesMapper

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PrepopulateCurrencyRatesDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val api: CurrencyRatesApi,
    private val userProfileDao: UserProfileDao,
    private val userPreferences: UserPreferences,
    private val currencyRatesDao: CurrencyRatesDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} Worker started. WorkId=${id}")

        val userUID = userPreferences.getUserIdLocally()
        if (userUID.isNullOrEmpty()) {
            Logger.e(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} User ID is null or empty. Cannot proceed.")
            return Result.retry()
        }

        val baseCurrency = userProfileDao.getUserProfile(userUID).baseCurrency
        val baseCurrencyCode = CountryMapper.toCurrencies(baseCurrency).keys.firstOrNull()

        if (baseCurrencyCode.isNullOrEmpty()) {
            Logger.e(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} Base currency is null or empty for user $userUID. Cannot proceed.")
            return Result.failure()
        }

        Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} Worker started for userId: $userUID")
        Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} baseCurrency: $baseCurrency")
        Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} baseCurrencyCode: $baseCurrencyCode")


        return try {
            val currencyRates = api.getExchangeRates(baseCurrency = baseCurrencyCode)

            val currencyRatesEntity =
                CurrencyRatesMapper.fromCurrencyResponseToEntity(currencyRates)

            Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} Currency rates entity: $currencyRatesEntity")

            currencyRatesDao.insertCurrencyRates(currencyRatesEntity)

            Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} Currency rates inserted successfully.")

            userPreferences.setCurrencyRatesUpdated(true)

            Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} Set Currency rates updated to TRUE successfully.")

            Result.success()
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}

