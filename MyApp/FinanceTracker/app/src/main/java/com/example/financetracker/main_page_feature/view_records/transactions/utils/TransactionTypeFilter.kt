package com.example.financetracker.main_page_feature.view_records.transactions.utils

sealed class TransactionTypeFilter {
    data object Income : TransactionTypeFilter()
    data object Expense : TransactionTypeFilter()
    data object Both : TransactionTypeFilter()
}
