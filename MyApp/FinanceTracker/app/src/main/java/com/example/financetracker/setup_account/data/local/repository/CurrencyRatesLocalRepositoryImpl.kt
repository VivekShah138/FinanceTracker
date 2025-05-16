package com.example.financetracker.setup_account.data.local.repository

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDao
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesMapper
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.PrepopulateCurrencyRatesDatabaseWorker
import com.example.financetracker.setup_account.domain.model.CurrencyResponse
import com.example.financetracker.setup_account.domain.repository.local.CurrencyRatesLocalRepository
import java.util.Calendar
import java.util.concurrent.TimeUnit

class CurrencyRatesLocalRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val currencyRatesDao: CurrencyRatesDao,
    private val workManager: WorkManager
): CurrencyRatesLocalRepository {

    override suspend fun getCurrencyRatesLocal(baseCurrency: String): CurrencyResponse? {
        return try {
            val currencyResponseEntity = currencyRatesDao.getCurrencyRates(baseCurrency)

            if (currencyResponseEntity == null) {
                Log.d("CurrencyRatesRepo", "No currency rates found locally for base currency: $baseCurrency")
                return null
            }

            val currencyResponse = CurrencyRatesMapper.fromEntityToCurrencyResponse(currencyResponseEntity)
            Log.d("CurrencyRatesRepo", "Successfully retrieved currency rates for $baseCurrency: $currencyResponse")

            currencyResponse
        } catch (e: Exception) {
            Log.e("CurrencyRatesRepo", "Error retrieving local currency rates for $baseCurrency: ${e.message}")
            e.printStackTrace()
            null
        }
    }


    override suspend fun insertCurrencyRatesLocalOneTime() {
        if (!userPreferences.getCurrencyRatesUpdated()) {
            Log.d("WorkManagerCurrencies", "Currency rates is not updated One Time Request Started.")
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

            Log.d("WorkManagerCurrencyRates", "One-time WorkManager task successfully enqueued.")
        } else {
            Log.d("WorkManagerCurrencyRates", "Currency rates already updated. Skipping one-time worker.")
        }
    }

    override suspend fun insertCurrencyRatesLocalPeriodically() {
        val workRequest = PeriodicWorkRequestBuilder<PrepopulateCurrencyRatesDatabaseWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS) // Start at 6:00 AM
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // Only run if internet is available
                    .build()
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL, // Ensures exponential retry delay
                10, TimeUnit.MINUTES       // Starts retrying after 10 minutes, doubles each time
            )
            .addTag("CurrencyUpdateWorker")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "CurrencyUpdateWorker",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, // Ensures it re-enqueues correctly if rescheduled
            workRequest
        )

        Log.d("WorkManagerCurrencyRates", "Scheduled daily currency update at 6:00 AM")
    }


    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if (now.after(targetTime)) {
            targetTime.add(Calendar.DAY_OF_YEAR, 1) // Schedule for next day if time has passed
        }

        return targetTime.timeInMillis - now.timeInMillis
    }
}