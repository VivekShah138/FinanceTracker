package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetCustomCategories (
   private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(type: String,uid: String): Flow<List<Category>> {
        return categoryRepository.getCustomCategories(type,uid)
    }
}