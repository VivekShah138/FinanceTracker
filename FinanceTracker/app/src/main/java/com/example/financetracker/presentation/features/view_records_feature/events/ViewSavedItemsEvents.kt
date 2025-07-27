package com.example.financetracker.presentation.features.view_records_feature.events

import com.example.financetracker.domain.model.SavedItems

sealed class ViewSavedItemsEvents() {
    data object LoadTransactions: ViewSavedItemsEvents()
    data class ChangeSearchSavedItem(val name: String): ViewSavedItemsEvents()
    data class FilterSavedItemList(val list: List<SavedItems>, val newWord: String): ViewSavedItemsEvents()

    // Transaction Selection
    data class ToggleSavedItemSelection(val savedItemsId: Int): ViewSavedItemsEvents()
    data object EnterSelectionMode: ViewSavedItemsEvents()
    data object ExitSelectionMode: ViewSavedItemsEvents()
    data object SelectAllItems: ViewSavedItemsEvents()

    // Deleted SavedItem
    data class ChangeCustomDateAlertBox(val state: Boolean): ViewSavedItemsEvents()
    data object DeleteSelectedSavedItems: ViewSavedItemsEvents()

    // Edit SavedItem
    data class ChangeUpdateBottomSheetState(val state: Boolean): ViewSavedItemsEvents()
    data class ChangeSavedItem(val itemName: String, val itemPrice: String, val itemShopName: String, val itemDescription: String): ViewSavedItemsEvents()
    data class SelectSavedItems(val savedItems: SavedItems): ViewSavedItemsEvents()
    data object SaveItem: ViewSavedItemsEvents()
    data class GetSingleSavedItem(val id: Int): ViewSavedItemsEvents()
    data object RefreshUpdatedItem: ViewSavedItemsEvents()
}