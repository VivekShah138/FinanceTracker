package com.example.financetracker.main_page_feature.view_records.transactions.presentation

sealed class ViewTransactionsEvents() {
    data object LoadTransactionsAll: ViewTransactionsEvents()
    data object LoadTransactionsToday: ViewTransactionsEvents()
    data object LoadTransactionsThisMonth: ViewTransactionsEvents()
    data object LoadTransactionsLastMonth: ViewTransactionsEvents()
    data object LoadTransactionsLast3Month: ViewTransactionsEvents()
    data class LoadTransactionsCustomDate(val fromDate: Long,val toDate: Long): ViewTransactionsEvents()
    data class SelectTransactionsDuration(val duration: String,val expanded: Boolean): ViewTransactionsEvents()
    data class SelectTransactionsFilter(val state: Boolean): ViewTransactionsEvents()
    data class ChangeCustomDateAlertBox(val state: Boolean): ViewTransactionsEvents()
}