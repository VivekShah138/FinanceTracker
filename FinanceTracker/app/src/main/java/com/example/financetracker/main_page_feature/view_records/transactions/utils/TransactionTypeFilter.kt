package com.example.financetracker.main_page_feature.view_records.transactions.utils

sealed class TransactionTypeFilter(val label: String) {
    data object Income : TransactionTypeFilter(label = "Income")
    data object Expense : TransactionTypeFilter(label = "Expense")
    data object Both : TransactionTypeFilter(label = "Both")
}
