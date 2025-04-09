package com.example.financetracker.core.local.domain.room.repository

import android.content.Context
import com.example.financetracker.core.local.domain.room.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(type: String,uid: String): Flow<List<Category>>
    suspend fun getCustomCategories(type: String,uid: String): Flow<List<Category>>
    suspend fun getPredefinedCategories(type: String): Flow<List<Category>>
    suspend fun insertCategories(categories: List<Category>)
    suspend fun insertCategory(category: Category)
    suspend fun getCategoriesCount(): Int
    suspend fun insertPredefinedCategories()
}