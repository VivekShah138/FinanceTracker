package com.example.financetracker.main_page_feature.view_records.transactions.presentation

sealed class ViewTransactionsEvents() {

    // Load Transaction
    data object LoadTransactionsAll: ViewTransactionsEvents()
    data object LoadTransactionsToday: ViewTransactionsEvents()
    data object LoadTransactionsThisMonth: ViewTransactionsEvents()
    data object LoadTransactionsLastMonth: ViewTransactionsEvents()
    data object LoadTransactionsLast3Month: ViewTransactionsEvents()
    data class LoadTransactionsCustomDate(val fromDate: Long,val toDate: Long): ViewTransactionsEvents()

    // Duration
    data class SelectTransactionsDuration(val duration: String,val expanded: Boolean): ViewTransactionsEvents()
    data class ChangeCustomDateAlertBox(val state: Boolean): ViewTransactionsEvents()

    // Filter BottomSheet
    data class SelectTransactionsFilter(val state: Boolean): ViewTransactionsEvents()

    // Transaction Selection
    data class ToggleTransactionSelection(val transactionId: Int): ViewTransactionsEvents()
    data object EnterSelectionMode: ViewTransactionsEvents()
    data object ExitSelectionMode: ViewTransactionsEvents()



    // After Selection Actions
    data object DeleteSelectedTransactions: ViewTransactionsEvents()


}