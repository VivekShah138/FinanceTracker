package com.example.financetracker.categories_feature.expense.presentation

import com.example.financetracker.core.local.domain.room.model.Category

data class ExpenseCategoriesStates (
    val predefinedCategories: List<Category> = emptyList(),
    val customCategories: List<Category> = emptyList(),
    val categoryName: String = "",
    val userUid: String = "",
    val categoryId: String? = null,
    val categoryType: String = "",
    val categoryAlertBoxState: Boolean = false
)