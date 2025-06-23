package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source

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
