package com.example.financetracker.core.local.domain.room.repository

import com.example.financetracker.core.local.domain.room.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(type: String): Flow<List<Category>>
    suspend fun insertCategories(categories: List<Category>)
}