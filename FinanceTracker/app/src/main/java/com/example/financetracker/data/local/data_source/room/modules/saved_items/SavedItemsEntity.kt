package com.example.financetracker.data.local.data_source.room.modules.saved_items

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class SavedItemsEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,
    val itemName: String,
    val itemCurrency: String,
    val itemPrice: Double?,
    val itemDescription: String?,
    val itemShopName: String?,
    val userUID: String,
    val cloudSync: Boolean
)
