package com.example.financetracker.domain.usecases.local.category

import com.example.financetracker.domain.repository.local.CategoryRepository

class DeleteCustomCategoriesLocalUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int) {
        return categoryRepository.deleteCategory(categoryId)
    }
}