package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.repository.local.CategoryRepository

class InsertCustomCategory(
    private val categoryRepository: CategoryRepository
){
    suspend operator fun invoke(category: Category){
        categoryRepository.insertCategory(category)
    }
}