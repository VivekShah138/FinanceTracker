package com.example.financetracker.main_page_feature.view_records.saved_items.presentation

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems

data class ViewSavedItemsStates(
    val savedItemsList: List<SavedItems> = emptyList(),

    // selected Saved Items
    val selectedSavedItems: Set<Int> = emptySet(),
    val isSelectionMode: Boolean = false,

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
