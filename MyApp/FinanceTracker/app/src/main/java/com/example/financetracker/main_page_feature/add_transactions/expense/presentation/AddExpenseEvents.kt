package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

sealed class AddExpenseEvents {
    data class SelectCategory(val categoryName: String, val bottomSheetState: Boolean): AddExpenseEvents()
    data class ChangeSavedItemState(val state: Boolean): AddExpenseEvents()
    data class LoadCategory(val type: String): AddExpenseEvents()
    data class ChangeTransactionName(val name: String): AddExpenseEvents()
}