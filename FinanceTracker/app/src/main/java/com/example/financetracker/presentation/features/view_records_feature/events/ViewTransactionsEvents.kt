package com.example.financetracker.presentation.features.view_records_feature.events

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.utils.DurationFilter
import com.example.financetracker.utils.TransactionFilter
import com.example.financetracker.utils.TransactionOrder
import com.example.financetracker.utils.TransactionTypeFilter

sealed class ViewTransactionsEvents() {

    // Duration
    data class SelectTransactionsDuration(val duration: DurationFilter, val expanded: Boolean): ViewTransactionsEvents()
    data class ChangeCustomDateAlertBox(val state: Boolean): ViewTransactionsEvents()

    // Filter BottomSheet
    data class SelectTransactionsFilter(val state: Boolean): ViewTransactionsEvents()
    data class SelectTransactionsFilterType(val type: TransactionTypeFilter): ViewTransactionsEvents()
    data class SelectTransactionsFilterCategories(val categories: List<Category>): ViewTransactionsEvents()
    data class SelectTransactionsFilterOrder(val order: TransactionOrder): ViewTransactionsEvents()
    data class UpdateFilter(val filter: TransactionFilter) : ViewTransactionsEvents()
    data object ApplyFilter: ViewTransactionsEvents()
    data class ClearFilter(val duration: DurationFilter): ViewTransactionsEvents()

    // Transaction Selection
    data class ToggleTransactionSelection(val transactionId: Int): ViewTransactionsEvents()
    data object EnterSelectionMode: ViewTransactionsEvents()
    data object ExitSelectionMode: ViewTransactionsEvents()
    data object SelectAllTransactions: ViewTransactionsEvents()
    data class SelectItems(val transactions: Transactions): ViewTransactionsEvents()


    // After Selection Actions
    data object DeleteSelectedTransactions: ViewTransactionsEvents()

    // Get Selected Transaction
    data class GetSingleTransaction(val transactionId: Int): ViewTransactionsEvents()


}