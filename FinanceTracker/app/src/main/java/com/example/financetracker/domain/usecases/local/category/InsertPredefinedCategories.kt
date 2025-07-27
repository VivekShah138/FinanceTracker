package com.example.financetracker.domain.usecases.local.category

import com.example.financetracker.domain.repository.local.CategoryRepository

class InsertPredefinedCategories(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() {
        return categoryRepository.insertPredefinedCategories()
    }
}