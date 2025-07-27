package com.example.financetracker.mapper

import com.example.financetracker.data.data_source.local.room.modules.saved_items.SavedItemsEntity
import com.example.financetracker.domain.model.SavedItems

object SavedItemsMapper {

    fun toEntity(savedItem: SavedItems): SavedItemsEntity {
        return SavedItemsEntity(
            itemId = savedItem.itemId ?: 0,
            itemName = savedItem.itemName,
            itemPrice = savedItem.itemPrice,
            itemDescription = savedItem.itemDescription,
            itemShopName = savedItem.itemShopName,
            itemCurrency = CountryMapper.fromCurrencies(savedItem.itemCurrency),
            userUID = savedItem.userUID,
            cloudSync = savedItem.cloudSync
        )
    }

    fun toDomain(entity: SavedItemsEntity): SavedItems {
        return SavedItems(
            itemId = entity.itemId,
            itemName = entity.itemName,
            itemPrice = entity.itemPrice,
            itemDescription = entity.itemDescription,
            itemShopName = entity.itemShopName,
            itemCurrency = CountryMapper.toCurrencies(entity.itemCurrency),
            userUID = entity.userUID,
            cloudSync = entity.cloudSync
        )
    }
}