package com.example.financetracker.core.local.domain.room.repository

import android.content.Context
import com.example.financetracker.core.local.domain.room.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(type: String): Flow<List<Category>>
    suspend fun insertCategories(categories: List<Category>)
    suspend fun getCategoriesCount(): Int
    suspend fun insertPredefinedCategories()
}