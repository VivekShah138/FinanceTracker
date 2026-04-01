package com.example.financetracker.data.repository.local

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.room.modules.currency_rates.CurrencyRatesDao
import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
import com.example.financetracker.mapper.CurrencyRatesMapper
import com.example.financetracker.worker.PrepopulateCurrencyRatesDatabaseWorker
import com.example.financetracker.domain.model.CurrencyResponse
import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository
import java.util.Calendar
import java.util.concurrent.TimeUnit

class CurrencyRatesLocalRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val currencyRatesDao: CurrencyRatesDao,
    private val workManager: WorkManager
): CurrencyRatesLocalRepository {

    override suspend fun getCurrencyRatesLocal(baseCurrency: String): CurrencyResponse? {
        return try {
            val currencyResponseEntity =
                currencyRatesDao.getCurrencyRates(baseCurrency) ?: return null

            val currencyResponse = CurrencyRatesMapper.fromEntityToCurrencyResponse(currencyResponseEntity)
            currencyResponse
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override suspend fun syncCurrencyRatesOnce() {
        if (!userPreferences.getCurrencyRatesUpdated()) {
            val workRequest = OneTimeWorkRequestBuilder<PrepopulateCurrencyRatesDatabaseWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    30, TimeUnit.SECONDS
                )
                .addTag("PrepopulateCurrencyRates")
                .build()

            workManager.enqueueUniqueWork(
                "PrepopulateCurrencyRates",
                ExistingWorkPolicy.KEEP,
                workRequest
            )

            Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} ENQUEUED. WorkId=${workRequest.id}")

        } else {
            Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} One time already enqueued.")
        }
    }

    override suspend fun syncCurrencyRatesPeriodically() {
        val workRequest = PeriodicWorkRequestBuilder<PrepopulateCurrencyRatesDatabaseWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                10, TimeUnit.MINUTES
            )
            .addTag("CurrencyUpdateWorker")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "CurrencyUpdateWorker",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )

        Logger.d(Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CURRENCY_RATES_TO_LOCAL_WORK_MANAGER} periodical worker enqueued.")
    }


    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if (now.after(targetTime)) {
            targetTime.add(Calendar.DAY_OF_YEAR, 1)
        }
        return targetTime.timeInMillis - now.timeInMillis
    }
}