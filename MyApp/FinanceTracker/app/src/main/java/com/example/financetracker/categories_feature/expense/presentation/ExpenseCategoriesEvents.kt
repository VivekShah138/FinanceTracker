package com.example.financetracker.categories_feature.expense.presentation

import com.example.financetracker.core.local.domain.room.model.Category

sealed class ExpenseCategoriesEvents {
    data class ChangeCategoryName(val name: String): ExpenseCategoriesEvents()
    data class ChangeCategoryAlertBoxState(val state: Boolean): ExpenseCategoriesEvents()
    data object SaveCategory: ExpenseCategoriesEvents()
    data object DeleteCategory: ExpenseCategoriesEvents()
    data class ChangeSelectedCategory(val category: Category): ExpenseCategoriesEvents()
}