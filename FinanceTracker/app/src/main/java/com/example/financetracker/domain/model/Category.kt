package com.example.financetracker.domain.model

data class Category(
    val categoryId: Int? = null,
    val uid: String?,
    val name: String,
    val type: String,
    val icon: String,
    val isCustom: Boolean
)

