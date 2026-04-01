package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.room.modules.country.CountryDao
import com.example.financetracker.data.data_source.remote.CountryApi
import com.example.financetracker.domain.model.toEntity
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
        Logger.d(Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER} Worker started. WorkId=${id}")


        val existingCount = countryDao.getCountryCount()
        if (existingCount > 0) {
            Logger.d(Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER} Countries details already inserted: $existingCount")
            return Result.success()
        }

        return try {
            val countries = api.getCountries()

            Logger.d(Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER} Received countries: ${countries.size}")


            val countryEntities = countries.map{it.toEntity()}
            Logger.d(Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER} Mapped to Entity: $countryEntities")

            countryDao.insertAll(countryEntities)

            val insertedData = countryDao.getAllCountries()

            Logger.d(Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER} Countries inserted successfully: ${insertedData.size}")

            Result.success()

        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_COUNTRY_TO_LOCAL_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}
