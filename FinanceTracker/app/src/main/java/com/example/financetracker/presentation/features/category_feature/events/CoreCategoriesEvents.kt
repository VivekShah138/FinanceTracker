package com.example.financetracker.presentation.features.category_feature.events

sealed class CoreCategoriesEvents {
    data class SelectCategoryType(val type: String): CoreCategoriesEvents()
    data object AddCategory: CoreCategoriesEvents()
    data class ChangeCategoryName(val name: String): CoreCategoriesEvents()
    data class ChangeCategoryAlertBoxState(val state: Boolean): CoreCategoriesEvents()
}
