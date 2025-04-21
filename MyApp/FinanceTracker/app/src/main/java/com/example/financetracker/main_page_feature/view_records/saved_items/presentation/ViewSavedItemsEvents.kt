package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents

sealed class ViewSavedItemsEvents() {
    data object LoadTransactions: ViewSavedItemsEvents()

    // Transaction Selection
    data class ToggleSavedItemSelection(val savedItemsId: Int): ViewSavedItemsEvents()
    data object EnterSelectionMode: ViewSavedItemsEvents()
    data object ExitSelectionMode: ViewSavedItemsEvents()

    // Deleted SavedItem
    data object DeleteSelectedSavedItems: ViewSavedItemsEvents()

    // Edit SavedItem
    data class ChangeUpdateBottomSheetState(val state: Boolean): ViewSavedItemsEvents()
    data class ChangeSavedItem(val itemName: String, val itemPrice: String, val itemShopName: String, val itemDescription: String): ViewSavedItemsEvents()
    data class SelectSavedItems(val savedItems: SavedItems): ViewSavedItemsEvents()
    data object SaveItem: ViewSavedItemsEvents()
}