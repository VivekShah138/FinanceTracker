package com.example.financetracker.domain.usecases.local.category

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.repository.local.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetPredefinedCategoriesByTypeLocalUseCase (
   private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(type: String): Flow<List<Category>> {
        return categoryRepository.getPredefinedCategoriesByType(type)
    }
}