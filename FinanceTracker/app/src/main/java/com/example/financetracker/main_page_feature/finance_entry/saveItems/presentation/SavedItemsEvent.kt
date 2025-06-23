package com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation

sealed class SavedItemsEvent {
    data class OnChangeItemName(val name: String): SavedItemsEvent()
    data class OnChangeItemDescription(val description: String): SavedItemsEvent()
    data class OnChangeItemPrice(val price: String): SavedItemsEvent()
    data class OnChangeItemShopName(val shopeName: String): SavedItemsEvent()
    data class OnChangeItemCurrency(
        val name: String,
        val symbol: String,
        val code: String,
        val expanded: Boolean
    ): SavedItemsEvent()
    data object LoadCurrencies: SavedItemsEvent()
    data object Submit: SavedItemsEvent()
}