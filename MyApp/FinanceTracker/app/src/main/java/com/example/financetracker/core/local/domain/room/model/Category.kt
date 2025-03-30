package com.example.financetracker.core.local.domain.room.model

import com.example.financetracker.core.local.data.room.data_source.category.CategoryEntity

data class Category(
    val name: String,
    val type: String,
    val icon: String
)

fun CategoryEntity.toDomain(): Category {
    return Category( name = name,
        type = type,
        icon = icon
    )
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(name = name,
        type = type,
        icon = icon
    )
}
