package com.example.financetracker.data.local.data_source.room.modules.saved_items

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DeletedSavedItemsEntity(
    @PrimaryKey
    val itemId: Int,
    val userUID: String
)
