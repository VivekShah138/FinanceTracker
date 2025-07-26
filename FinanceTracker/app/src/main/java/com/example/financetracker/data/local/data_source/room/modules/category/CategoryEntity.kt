package com.example.financetracker.data.local.data_source.room.modules.category

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CategoryEntity(

    @PrimaryKey(autoGenerate = true)
    val categoryId: Int,
    val uid: String?,
    val name: String,
    val type: String,
    val icon: String,
    val isCustom: Boolean
)