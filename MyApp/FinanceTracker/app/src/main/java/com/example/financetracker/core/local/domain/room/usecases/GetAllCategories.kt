package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategories @Inject constructor(
   private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(type: String,uid: String): Flow<List<Category>> {
        return categoryRepository.getAllCategories(type,uid)
    }
}