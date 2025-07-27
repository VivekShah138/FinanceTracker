package com.example.financetracker.categories_feature.core.presentation

import com.example.financetracker.domain.model.Category

data class CategoriesStates (
    val predefinedCategories: List<Category> = emptyList(),
    val customCategories: List<Category> = emptyList(),
    val categoryName: String = "",
    val userUid: String = "",
    val categoryId: String? = null,
    val categoryType: String = "",
    val updateCategoryAlertBoxState: Boolean = false,
    val addCategoryAlertBoxState: Boolean = false,
)