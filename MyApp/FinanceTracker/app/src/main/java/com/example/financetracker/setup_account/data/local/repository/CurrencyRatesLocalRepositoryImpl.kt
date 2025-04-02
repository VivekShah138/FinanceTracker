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
import com.example.financetracker.setup_account.data.local.data_source.country.PrepopulateCountryDatabaseWorker
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesDao
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.CurrencyRatesMapper
import com.example.financetracker.setup_account.data.local.data_source.currency_rates.PrepopulateCurrencyRatesDatabaseWorker
import com.example.financetracker.setup_account.domain.model.CurrencyResponse
import com.example.financetracker.setup_account.domain.repository.local.CurrencyRatesLocalRepository
import java.util.Calendar
import java.util.concurrent.TimeUnit

class CurrencyRatesLocalRepositoryImpl(
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

            val currencyResponse = CurrencyRatesMapper.fromEntityToResponse(currencyResponseEntity)
            Log.d("CurrencyRatesRepo", "Successfully retrieved currency rates for $baseCurrency: $currencyResponse")

            currencyResponse
        } catch (e: Exception) {
            Log.e("CurrencyRatesRepo", "Error retrieving local currency rates for $baseCurrency: ${e.message}")
            e.printStackTrace()
            null
        }
    }


    override suspend fun insertCurrencyRatesLocal() {
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // Ensures work runs only when connected
                .build()

            val workRequest = OneTimeWorkRequestBuilder<PrepopulateCurrencyRatesDatabaseWorker>()
//            .setConstraints(constraints) // Uncomment if network check is needed
                .setBackoffCriteria( // Set retry strategy
                    BackoffPolicy.EXPONENTIAL,
                    30, TimeUnit.SECONDS // Minimum delay before retry
                )
                .addTag("PrepopulateCurrencyRates") // Corrected tag name for better tracking
                .build()

            Log.d("WorkManagerCurrencyRates", "Enqueuing WorkManager task: $workRequest")

            workManager.enqueueUniqueWork(
                "PrepopulateCurrencyRates",  // Unique work name for currency rates
                ExistingWorkPolicy.KEEP,  // Prevents duplicate work requests
                workRequest
            )

            Log.d("WorkManagerCurrencyRates", "WorkManager task successfully enqueued.")
        } catch (e: Exception) {
            Log.e("WorkManagerCurrencyRates", "Error enqueuing WorkManager task: ${e.message}")
            e.printStackTrace()
        }
    }
}