package com.example.financetracker.core.local.data.room.data_source

import androidx.room.Entity


@Entity(primaryKeys = ["name", "type"])
data class CategoryEntity(
    val name: String,
    val type: String,
    val icon: String
)