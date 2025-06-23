package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DeletedSavedItemsEntity(
    @PrimaryKey
    val itemId: Int,
    val userUID: String
)
