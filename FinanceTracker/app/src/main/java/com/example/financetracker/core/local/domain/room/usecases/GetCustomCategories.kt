package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.repository.local.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCustomCategories (
   private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(type: String,uid: String): Flow<List<Category>> {
        return categoryRepository.getCustomCategories(type,uid)
    }
}