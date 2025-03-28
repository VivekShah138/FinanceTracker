package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import com.example.financetracker.core.local.domain.room.model.Category

data class AddExpenseStates(
    val bottomSheetState: Boolean = false,
    val category: String = "Select a Category",
    val categoryList: List<Category> = emptyList()
)
