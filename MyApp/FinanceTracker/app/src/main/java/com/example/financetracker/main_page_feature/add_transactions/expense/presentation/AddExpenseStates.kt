package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import com.example.financetracker.core.local.domain.room.model.Category

data class AddExpenseStates(

    // Category
    val bottomSheetState: Boolean = false,
    val category: String = "",
    val categoryList: List<Category> = emptyList(),

    //SavedItem
    val saveItemState: Boolean = false,

    //Transaction Name
    val transactionName: String = "",
)
