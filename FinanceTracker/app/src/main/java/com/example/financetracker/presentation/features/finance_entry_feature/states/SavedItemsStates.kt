package com.example.financetracker.presentation.features.finance_entry_feature.states

import com.example.financetracker.domain.model.Country

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
