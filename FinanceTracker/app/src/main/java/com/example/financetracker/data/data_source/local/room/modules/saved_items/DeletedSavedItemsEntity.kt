package com.example.financetracker.data.data_source.local.room.modules.saved_items

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DeletedSavedItemsEntity(
    @PrimaryKey
    val itemId: Int,
    val userUID: String
)
