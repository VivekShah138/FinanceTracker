package com.example.financetracker.setup_account.data.local.data_source.currency_rates

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDao
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.data.remote.CurrencyRatesApi

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
        val TAG = "WorkManagerCurrencies"
        Log.d(TAG, "Worker started")

        val userUID = userPreferences.getUserIdLocally()
        if (userUID.isNullOrEmpty()) {
            Log.e(TAG, "User ID is null or empty. Cannot proceed.")
            return Result.failure()
        }

        val baseCurrency = userProfileDao.getUserProfile(userUID)?.baseCurrency
        val baseCurrencyCode = CountryMapper.toCurrencies(baseCurrency)?.keys?.firstOrNull()

        if (baseCurrencyCode.isNullOrEmpty()) {
            Log.e(TAG, "Base currency is null or empty for user $userUID. Cannot proceed.")
            return Result.failure()
        }

        return try {
            Log.d(TAG, "Fetching exchange rates for base currency: $baseCurrencyCode")
            val currencyRates = try {
                api.getExchangeRates(baseCurrency = baseCurrencyCode)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching exchange rates from API: ${e.message}")

                return if (e is java.net.UnknownHostException || e is java.net.ConnectException) {
                    Log.d(TAG, "No Internet. Retrying in 30 seconds...")
                    Result.retry()  // Retry after backoff delay (30s)
                } else {
                    Result.failure()
                }
            }

            val currencyRatesEntity = CurrencyRatesMapper.fromCurrencyResponseToEntity(currencyRates)
            currencyRatesDao.insertCurrencyRates(currencyRatesEntity)
            Log.d(TAG, "Currency rates inserted successfully.")
            userPreferences.setCurrencyRatesUpdated(true)
            Log.d(TAG, "Set Currency rates updated to TRUE successfully.")

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error inserting currency rates: ${e.message}")
            Result.failure()
        }
    }
}

