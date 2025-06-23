package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetFirstTimeInstalled
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetFirstTimeInstalled

data class PredefinedCategoriesUseCaseWrapper(
    val getAllCategories: GetAllCategories,
    val insertPredefinedCategories: InsertPredefinedCategories,
    val getCustomCategories: GetCustomCategories,
    val getPredefinedCategories: GetPredefinedCategories,
    val insertCustomCategories: InsertCustomCategories,
    val deleteCustomCategories: DeleteCustomCategories,
)