package com.example.financetracker.domain.usecases.local.category

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.repository.local.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategories @Inject constructor(
   private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(type: String,uid: String): Flow<List<Category>> {
        return categoryRepository.getAllCategories(type,uid)
    }
}