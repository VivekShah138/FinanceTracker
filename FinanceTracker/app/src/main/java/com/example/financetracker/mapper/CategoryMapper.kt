package com.example.financetracker.mapper

import com.example.financetracker.data.data_source.local.room.modules.category.CategoryEntity
import com.example.financetracker.domain.model.Category

object CategoryMapper {
    fun toEntity(category: Category): CategoryEntity {
        return CategoryEntity(
            uid = category.uid,
            name = category.name,
            type = category.type,
            icon = category.icon,
            isCustom = category.isCustom,
            categoryId = category.categoryId ?: 0
        )
    }

    fun toDomain(entity: CategoryEntity): Category {
        return Category(
            categoryId = entity.categoryId,
            uid = entity.uid,
            name = entity.name,
            type = entity.type,
            icon = entity.icon,
            isCustom = entity.isCustom
        )
    }
}