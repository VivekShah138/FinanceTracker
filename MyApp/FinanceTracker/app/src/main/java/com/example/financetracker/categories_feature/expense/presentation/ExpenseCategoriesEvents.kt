package com.example.financetracker.categories_feature.expense.presentation

sealed class ExpenseCategoriesEvents {
    data class ChangeCategoryName(val name: String): ExpenseCategoriesEvents()
    data object SaveCategory: ExpenseCategoriesEvents()
}