package com.example.financetracker.presentation.features.view_records_feature.states

import com.example.financetracker.domain.model.SavedItems

data class ViewSavedItemsStates(
    val savedItemsList: List<SavedItems> = emptyList(),
    val savedItemsFilteredList: List<SavedItems> = emptyList(),

    val savedItem: String = "",

    // selected Saved Items
    val selectedSavedItems: Set<Int> = emptySet(),
    val isSelectionMode: Boolean = false,
    val customDeleteAlertBoxState: Boolean = false,

    // Update Saved Item
    val updatedItemId: Int = 0,
    val updateBottomSheetState: Boolean = false,
    val updatedItemName: String = "",
    val updatedItemPrice: String = "",
    val updatedItemShopName: String = "",
    val updatedItemDescription: String = "",
    val updatedItemCurrencyName: String = "",
    val updatedItemCurrencySymbol: String = "",
    val updatedItemCurrencyCode: String = "",
)
