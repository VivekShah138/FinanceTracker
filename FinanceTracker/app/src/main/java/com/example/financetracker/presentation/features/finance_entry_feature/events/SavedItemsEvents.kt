package com.example.financetracker.presentation.features.finance_entry_feature.events

sealed class SavedItemsEvents {
    data class OnChangeItemName(val name: String): SavedItemsEvents()
    data class OnChangeItemDescription(val description: String): SavedItemsEvents()
    data class OnChangeItemPrice(val price: String): SavedItemsEvents()
    data class OnChangeItemShopName(val shopeName: String): SavedItemsEvents()
    data class OnChangeItemCurrency(
        val name: String,
        val symbol: String,
        val code: String,
        val expanded: Boolean
    ): SavedItemsEvents()
    data object LoadCurrencies: SavedItemsEvents()
    data object Submit: SavedItemsEvents()
}