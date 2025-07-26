package com.example.financetracker.data.repository.local

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.data.data_source.local.room.modules.category.CategoryDao
import com.example.financetracker.worker.PrepopulateCategoryDatabaseWorker
import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.repository.local.CategoryRepository
import com.example.financetracker.mapper.CategoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val workManager: WorkManager
): CategoryRepository {
    override suspend fun getAllCategories(type: String, uid: String): Flow<List<Category>> {
        return categoryDao.getCategories(type = type,uid = uid).map { entities ->
            entities.map {
                CategoryMapper.toDomain(it)
            }
        }
    }

    override suspend fun getCustomCategories(type: String, uid: String): Flow<List<Category>> {
        return categoryDao.getCustomCategories(type = type,uid = uid).map { entities ->
            entities.map {
                CategoryMapper.toDomain(it)
            }
        }
    }

    override suspend fun getPredefinedCategories(type: String): Flow<List<Category>> {
        return categoryDao.getPredefinedCategories(type = type).map { entities ->
            entities.map {
                CategoryMapper.toDomain(it)
            }
        }
    }


    override suspend fun insertCategories(categories: List<Category>) {
        return categoryDao.insertCategories(
            categories = categories.map {
                CategoryMapper.toEntity(it)
            }
        )
    }

    override suspend fun insertCategory(category: Category) {
        return categoryDao.insertCategory(
            category = CategoryMapper.toEntity(category)
        )
    }

    override suspend fun deleteCategory(categoryId: Int) {
        categoryDao.deleteCustomCategory(categoryId = categoryId)
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