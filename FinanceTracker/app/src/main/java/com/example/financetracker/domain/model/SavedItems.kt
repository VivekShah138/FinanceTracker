package com.example.financetracker.domain.model

data class SavedItems(
    val itemId: Int? = null,
    val itemName: String = "",
    val itemCurrency: Map<String, Currency> = emptyMap(),
    val itemPrice: Double? = null,
    val itemDescription: String? = null,
    val itemShopName: String? = null,
    val userUID: String = "",
    val cloudSync: Boolean = false
)
