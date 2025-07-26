package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(type: String, uid: String): Flow<List<Category>>
    suspend fun getCustomCategories(type: String,uid: String): Flow<List<Category>>
    suspend fun getPredefinedCategories(type: String): Flow<List<Category>>
    suspend fun insertCategories(categories: List<Category>)
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(categoryId: Int)
    suspend fun getCategoriesCount(): Int
    suspend fun insertPredefinedCategories()
}