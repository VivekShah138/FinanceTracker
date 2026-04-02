package com.example.financetracker.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.room.modules.category.CategoryDao
import com.example.financetracker.mapper.CategoryMapper
import com.example.financetracker.utils.JsonCategoryMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class PrepopulateCategoryDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val categoryDao: CategoryDao,
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER} Worker started. WorkId=${id}")

        if(categoryDao.getCategoryCount() > 0) {
            Logger.d(Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER} Categories already exist. Skipping prepopulation.")
            return Result.success()
        }

        return try {
            Logger.d(Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER} Inserting predefined categories.")
            val jsonString = JsonCategoryMapper.loadJsonFromAssets(context, "categories.json")
            if (jsonString != null) {
                Logger.d(Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER} jsonString: $jsonString")
            }
            jsonString?.let { it ->
                val predefinedCategories = JsonCategoryMapper.parseJsonToCategories(jsonString = it,uid = "predefined")
                Logger.d(Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER} predefinedCategories: $predefinedCategories")
                categoryDao.insertCategories(
                    categories = predefinedCategories.map {
                        CategoryMapper.toEntity(it)
                    }
                )
            }
            Logger.d(Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER} Predefined categories inserted successfully.")

            Result.success()
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_CATEGORY_TO_LOCAL_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}