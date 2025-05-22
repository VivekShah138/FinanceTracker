package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model

import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.SavedItemsEntity
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.domain.model.Currency

data class SavedItems(
    val itemId: Int? = null,
    val itemName: String = "",
    val itemCurrency: Map<String,Currency> = emptyMap(),
    val itemPrice: Double? = null,
    val itemDescription: String? = null,
    val itemShopName: String? = null,
    val userUID: String = "",
    val cloudSync: Boolean = false
)

fun SavedItems.toEntity(): SavedItemsEntity {
    return SavedItemsEntity(
        itemId = itemId ?: 0,
        itemName = this.itemName,
        itemPrice = this.itemPrice,
        itemDescription = this.itemDescription,
        itemShopName = this.itemShopName,
        itemCurrency = CountryMapper.fromCurrencies(this.itemCurrency),
        userUID = this.userUID,
        cloudSync = this.cloudSync
    )
}

fun SavedItemsEntity.toDomain(): SavedItems {
    return SavedItems(
        itemName = this.itemName,
        itemPrice = this.itemPrice,
        itemDescription = this.itemDescription,
        itemShopName = this.itemShopName,
        itemCurrency = CountryMapper.toCurrencies(this.itemCurrency),
        userUID = this.userUID,
        itemId = this.itemId,
        cloudSync = this.cloudSync
    )
}