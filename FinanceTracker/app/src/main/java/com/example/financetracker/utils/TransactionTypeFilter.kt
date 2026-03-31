package com.example.financetracker.utils

sealed class TransactionTypeFilter(val label: String) {
    data object Income : TransactionTypeFilter(label = "Income")
    data object Expense : TransactionTypeFilter(label = "Expense")
    data object Both : TransactionTypeFilter(label = "Both")
}
