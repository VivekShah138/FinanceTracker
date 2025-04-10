package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository

class InsertCustomCategories(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        return categoryRepository.insertCategory(category)
    }
}