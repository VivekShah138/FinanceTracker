package com.example.financetracker.domain.usecases.local.category

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.repository.local.CategoryRepository

class InsertCustomCategoriesLocalUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        return categoryRepository.insertCategory(category)
    }
}