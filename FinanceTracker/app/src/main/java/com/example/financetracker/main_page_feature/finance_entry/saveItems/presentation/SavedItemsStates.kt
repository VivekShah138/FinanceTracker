package com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation

import com.example.financetracker.setup_account.domain.model.Country

data class SavedItemsStates(
    val itemName: String = "",
    val itemPrice: String = "",
    val itemCurrenciesList: List<Country> = emptyList(),
    val itemCurrencyName: String = "",
    val itemCurrencySymbol: String = "",
    val itemCurrencyCode: String = "",
    val itemCurrencyExpanded: Boolean = false,
    val itemShopName: String = "",
    val itemDescription: String = ""
)
