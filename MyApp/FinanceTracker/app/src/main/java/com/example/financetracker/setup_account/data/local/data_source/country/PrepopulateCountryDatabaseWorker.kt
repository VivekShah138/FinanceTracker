package com.example.financetracker.setup_account.data.local.data_source.country

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.setup_account.data.remote.CountryApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PrepopulateCountryDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val countryDao: CountryDao,
    private val api: CountryApi
) : CoroutineWorker(context, workerParams){

    override suspend fun doWork(): Result {
        Log.d("WorkManagerCountries", "Worker started")

        if (countryDao.getCountryCount() > 0) {
            Log.d("WorkManagerCountries", "Countries Already Inserted ${countryDao.getCountryCount()}")
            return Result.success()
        }

        return try {
            Log.d("WorkManagerCountries", "Fetching countries from API...")

            val countries = try {
                api.getCountries()
            } catch (e: Exception) {
                Log.e("WorkManagerCountries", "Error fetching countries from API: ${e.message}")

                // Check if error is due to no internet
                return if (e is java.net.UnknownHostException || e is java.net.ConnectException) {
                    Log.d("WorkManagerCountries", "No Internet. Retrying...")
                    Result.retry() // Will retry when network is available
                } else {
                    Result.failure()
                }
            }

            Log.d("WorkManagerCountries", "Received countries to insert: $countries")

            val countryEntities = countries.map { country ->
                try {
                    val entity = CountryMapper.fromCountryResponseToEntity(country)
                    Log.d("WorkManagerCountries", "Mapped to Entity: $entity")
                    entity
                } catch (e: Exception) {
                    Log.e("WorkManagerCountries", "Error mapping country: $country, Error: ${e.message}")
                    throw e
                }
            }

            Log.d("WorkManagerCountries", "Inserting Countries into Room: $countryEntities")
            countryDao.insertAll(countryEntities)

//             Verify insertion
            val insertedData = countryDao.getAllCountries()
            Log.d("WorkManagerCountries", "Retrieved After Insertion: $insertedData")

            Log.d("WorkManagerCountries", "Countries inserted successfully.")
            Result.success()
        } catch (e: Exception) {
            Log.e("WorkManagerCountries", "Error inserting countries: ${e.message}")
            e.printStackTrace()
            Result.failure()
        }
    }
}