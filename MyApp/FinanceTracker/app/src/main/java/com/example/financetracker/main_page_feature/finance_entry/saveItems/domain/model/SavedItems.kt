package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model

import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local.SavedItemsEntity
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.domain.model.Currency

data class SavedItems(
    val itemId: Int? = null,
    val itemName: String,
    val itemCurrency: Map<String,Currency>,
    val itemPrice: Double?,
    val itemDescription: String?,
    val itemShopName: String?,
    val userUID: String
)

fun SavedItems.toEntity(): SavedItemsEntity {
    return SavedItemsEntity(
        itemId = 0,
        itemName = this.itemName,
        itemPrice = this.itemPrice,
        itemDescription = this.itemDescription,
        itemShopName = this.itemShopName,
        itemCurrency = CountryMapper.fromCurrencies(this.itemCurrency),
        userUID = this.userUID
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
        itemId = this.itemId
    )
}