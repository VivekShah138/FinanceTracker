package com.example.financetracker.core.local.data.room.data_source.category

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.core.local.domain.room.model.toEntity
import com.example.financetracker.core.local.domain.room.utils.JsonUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class PrepopulateCategoryDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val categoryDao: CategoryDao,
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Log.d("WorkManager", "Worker started")

        if(categoryDao.getCategoryCount() > 0) {
            Log.d("WorkManager", "Categories already exist. Skipping prepopulation.")
            return Result.success()
        }

        return try {
            Log.d("WorkManager", "Inserting predefined categories...")

            Log.d("WorkManager","Reached CategoryImplementation")
            val jsonString = JsonUtils.loadJsonFromAssets(context, "categories.json")
            if (jsonString != null) {
                Log.d("WorkManager","Reached CategoryImplementation inside if jsonString not null")
                Log.d("WorkManager","jsonString: $jsonString")
            }
            jsonString?.let { it ->
                val predefinedCategories = JsonUtils.parseJsonToCategories(jsonString = it,uid = "predefined")
                Log.d("WorkManager","predefinedCategories: $predefinedCategories")
                categoryDao.insertCategories(
                    categories = predefinedCategories.map {
                        it.toEntity()
                    }
                )
            }
            Log.d("WorkManager", "Predefined categories inserted successfully.")
            Result.success()
        } catch (e: Exception) {
            Log.e("WorkManager", "Error inserting categories: ${e.message}")
            e.printStackTrace()
            Result.failure()
        }
    }
}