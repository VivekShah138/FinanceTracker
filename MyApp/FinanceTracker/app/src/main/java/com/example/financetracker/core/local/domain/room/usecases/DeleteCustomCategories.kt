package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.core.local.domain.room.repository.CategoryRepository

class DeleteCustomCategories(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int) {
        return categoryRepository.deleteCategory(categoryId)
    }
}