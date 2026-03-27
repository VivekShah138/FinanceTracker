package com.example.financetracker.data.repository.local

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.data.data_source.local.room.modules.country.CountryDao
import com.example.financetracker.worker.PrepopulateCountryDatabaseWorker
import com.example.financetracker.domain.model.Country
import com.example.financetracker.domain.model.toDomain
import com.example.financetracker.domain.model.toEntity
import com.example.financetracker.domain.repository.local.CountryLocalRepository
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
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PrepopulateCountryDatabaseWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                30, TimeUnit.SECONDS
            )
            .addTag("PrepopulateCountries")
            .build()

        Log.d("WorkManagerCountries", "WorkManager enqueued: $workRequest")
        workManager.enqueueUniqueWork(
            "PrepopulateCountries",
            ExistingWorkPolicy.KEEP,
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