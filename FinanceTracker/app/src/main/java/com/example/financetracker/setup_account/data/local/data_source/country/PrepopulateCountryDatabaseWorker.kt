package com.example.financetracker.setup_account.data.local.data_source.country

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.setup_account.data.remote.CountryApi
import com.example.financetracker.setup_account.domain.model.toEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException
import java.net.ProtocolException
import java.net.SocketTimeoutException

@HiltWorker
class PrepopulateCountryDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val countryDao: CountryDao,
    private val api: CountryApi
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("WorkManagerCountries", "Worker started")

        val existingCount = countryDao.getCountryCount()
        if (existingCount > 0) {
            Log.d("WorkManagerCountries", "Countries already inserted: $existingCount")
            return Result.success()
        }

        return try {
            Log.d("WorkManagerCountries", "Fetching countries from API...")

            val countries = try {
                api.getCountries()
            } catch (e: Exception) {
                Log.e("WorkManagerCountries", "Error fetching countries from API", e)

                return when (e) {
                    is SocketTimeoutException,
                    is ProtocolException,
                    is HttpException,
                    is IOException -> {
                        Log.d("WorkManagerCountries", "Recoverable error: ${e::class.java.simpleName}, retrying...")
                        Result.retry()
                    }
                    else -> {
                        Log.d("WorkManagerCountries", "Unrecoverable: ${e::class.java.simpleName}")
                        Result.failure()
                    }
                }
            }

            Log.d("WorkManagerCountries", "Received countries: ${countries.size}")

            val countryEntities = countries.map { country ->
                try {
                    val entity = country.toEntity()
                    Log.d("WorkManagerCountries", "Mapped to Entity: $entity")
                    entity
                } catch (e: Exception) {
                    Log.e("WorkManagerCountries", "Error mapping country: $country", e)
                    throw e
                }
            }

            Log.d("WorkManagerCountries", "Inserting countries into Room...")
            countryDao.insertAll(countryEntities)

            val insertedData = countryDao.getAllCountries()
            Log.d("WorkManagerCountries", "Countries inserted successfully: ${insertedData.size}")
            Result.success()

        } catch (e: Exception) {
            Log.e("WorkManagerCountries", "Unexpected error occurred", e)
            Result.failure()
        }
    }
}
