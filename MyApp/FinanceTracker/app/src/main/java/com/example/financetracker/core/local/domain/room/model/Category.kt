package com.example.financetracker.core.local.domain.room.model

import com.example.financetracker.core.local.data.room.data_source.category.CategoryEntity

data class Category(
    val categoryId: Int? = null,
    val uid: String?,
    val name: String,
    val type: String,
    val icon: String,
    val isCustom: Boolean
)

fun CategoryEntity.toDomain(): Category {
    return Category(
        uid = uid,
        name = name,
        type = type,
        icon = icon,
        isCustom = isCustom
    )
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        uid = uid,
        name = name,
        type = type,
        icon = icon,
        isCustom = isCustom,
        categoryId = 0
    )
}
