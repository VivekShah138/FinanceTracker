package com.example.financetracker.presentation.features.finance_entry_feature.events

sealed class AddSavedItemsEvents {
    data class OnChangeItemName(val name: String): AddSavedItemsEvents()
    data class OnChangeItemDescription(val description: String): AddSavedItemsEvents()
    data class OnChangeItemPrice(val price: String): AddSavedItemsEvents()
    data class OnChangeItemShopName(val shopName: String): AddSavedItemsEvents()
    data class OnChangeItemCurrency(
        val name: String,
        val symbol: String,
        val code: String,
        val expanded: Boolean
    ): AddSavedItemsEvents()
    data object LoadCurrencies: AddSavedItemsEvents()
    data object Submit: AddSavedItemsEvents()
}