package com.example.financetracker.core.local.data.room.repository

import com.example.financetracker.core.local.data.room.data_source.CategoryDao
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.model.toDomain
import com.example.financetracker.core.local.domain.room.model.toEntity
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
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
}