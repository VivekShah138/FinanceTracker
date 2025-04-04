package com.example.financetracker.core.local.data.room.repository

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.core.local.data.room.data_source.category.CategoryDao
import com.example.financetracker.core.local.data.room.data_source.category.PrepopulateCategoryDatabaseWorker
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.model.toDomain
import com.example.financetracker.core.local.domain.room.model.toEntity
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.room.utils.JsonUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
//    private val context: Context,
    private val workManager: WorkManager
): CategoryRepository {
    override suspend fun getCategories(type: String): Flow<List<Category>> {
        return categoryDao.getCategories(type).map { entities ->
            entities.map {
                it.toDomain()
            }
        }
    }


    override suspend fun insertCategories(categories: List<Category>) {
        return categoryDao.insertCategories(
            categories = categories.map {
                it.toEntity()
            }
        )
    }

    override suspend fun insertCategory(category: Category) {
        return categoryDao.insertCategory(
            category = category.toEntity()
        )
    }

    override suspend fun getCategoriesCount(): Int {
        return categoryDao.getCategoryCount()
    }

    override suspend fun insertPredefinedCategories() {
        val workRequest = OneTimeWorkRequestBuilder<PrepopulateCategoryDatabaseWorker>()
            .build()
        workManager.enqueueUniqueWork(
            "prepopulate_db",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}