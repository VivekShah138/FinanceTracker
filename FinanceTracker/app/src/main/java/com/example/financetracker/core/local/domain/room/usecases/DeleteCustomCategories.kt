package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.domain.repository.local.CategoryRepository

class DeleteCustomCategories(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int) {
        return categoryRepository.deleteCategory(categoryId)
    }
}