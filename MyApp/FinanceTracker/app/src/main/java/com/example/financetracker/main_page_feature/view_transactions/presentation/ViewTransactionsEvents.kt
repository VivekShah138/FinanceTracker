package com.example.financetracker.main_page_feature.view_transactions.presentation

sealed class ViewTransactionsEvents() {
    data object LoadTransactions: ViewTransactionsEvents()
}