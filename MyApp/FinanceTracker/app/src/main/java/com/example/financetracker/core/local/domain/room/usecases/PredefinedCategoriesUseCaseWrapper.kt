package com.example.financetracker.core.local.domain.room.usecases

data class PredefinedCategoriesUseCaseWrapper(
    val getAllCategories: GetAllCategories,
    val insertPredefinedCategories: InsertPredefinedCategories,
    val getCustomCategories: GetCustomCategories,
    val getPredefinedCategories: GetPredefinedCategories
)