package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents

sealed class ViewSavedItemsEvents() {
    data object LoadTransactions: ViewSavedItemsEvents()

    // Transaction Selection
    data class ToggleSavedItemSelection(val savedItemsId: Int): ViewSavedItemsEvents()
    data object EnterSelectionMode: ViewSavedItemsEvents()
    data object ExitSelectionMode: ViewSavedItemsEvents()



    // After Selection Actions
    data object DeleteSelectedSavedItems: ViewSavedItemsEvents()
}