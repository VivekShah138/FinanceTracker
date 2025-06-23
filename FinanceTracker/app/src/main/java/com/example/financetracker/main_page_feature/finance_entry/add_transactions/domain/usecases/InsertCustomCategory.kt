package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository

class InsertCustomCategory(
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(category: Category){
        categoryRepository.insertCategory(category)
    }
}