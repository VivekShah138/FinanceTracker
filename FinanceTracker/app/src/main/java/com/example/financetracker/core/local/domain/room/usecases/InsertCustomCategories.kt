package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.repository.local.CategoryRepository

class InsertCustomCategories(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        return categoryRepository.insertCategory(category)
    }
}