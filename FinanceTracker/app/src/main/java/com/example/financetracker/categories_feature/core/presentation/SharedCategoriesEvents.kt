package com.example.financetracker.categories_feature.core.presentation

import com.example.financetracker.domain.model.Category

sealed class SharedCategoriesEvents {
    data class ChangeCategoryName(val name: String): SharedCategoriesEvents()
    data class ChangeCategoryAlertBoxState(val state: Boolean): SharedCategoriesEvents()
    data object SaveCategory: SharedCategoriesEvents()
    data object DeleteCategory: SharedCategoriesEvents()
    data class ChangeSelectedCategory(val category: Category): SharedCategoriesEvents()
}