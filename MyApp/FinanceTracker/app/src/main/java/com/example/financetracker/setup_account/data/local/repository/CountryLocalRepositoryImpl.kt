package com.example.financetracker.setup_account.data.local.repository

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.setup_account.data.local.data_source.country.CountryDao
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.data.local.data_source.country.PrepopulateCountryDatabaseWorker
import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.model.toDomain
import com.example.financetracker.setup_account.domain.model.toEntity
import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountryLocalRepositoryImpl @Inject constructor(
    private val countryDao: CountryDao,
    private val workManager: WorkManager
): CountryLocalRepository {

    override suspend fun getCountries(): List<Country> {
        val countries = countryDao.getAllCountries().map {
            it.toDomain()
        }
        Log.d("RoomDatabase", "Fetched Countries from Room: $countries")
        return countries
    }

    override suspend fun insertCountries() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Ensures work runs only when connected
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PrepopulateCountryDatabaseWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria( // Set retry strategy
                BackoffPolicy.LINEAR,
                30, TimeUnit.SECONDS // Minimum delay before retry
            )
            .addTag("PrepopulateCountries") // Add a tag for tracking logs
            .build()

        Log.d("WorkManagerCountries", "WorkManager enqueued: $workRequest")
        workManager.enqueueUniqueWork(
            "PrepopulateCountries",
            ExistingWorkPolicy.KEEP,  // Ensures it runs even after app restart
            workRequest
        )
    }

    override suspend fun insertCountries(countries: List<Country>) {
        val countriesEntity = countries.map {
            it.toEntity()
        }
        countryDao.insertAll(countriesEntity)
    }


}