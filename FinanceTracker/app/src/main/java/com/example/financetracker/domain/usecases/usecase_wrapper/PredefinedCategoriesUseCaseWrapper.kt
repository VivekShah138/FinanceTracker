package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.category.DeleteCustomCategories
import com.example.financetracker.domain.usecases.local.category.GetAllCategories
import com.example.financetracker.domain.usecases.local.category.GetCustomCategories
import com.example.financetracker.domain.usecases.local.category.GetPredefinedCategories
import com.example.financetracker.domain.usecases.local.category.InsertCustomCategories
import com.example.financetracker.domain.usecases.local.category.InsertPredefinedCategories

data class PredefinedCategoriesUseCaseWrapper(
    val getAllCategories: GetAllCategories,
    val insertPredefinedCategories: InsertPredefinedCategories,
    val getCustomCategories: GetCustomCategories,
    val getPredefinedCategories: GetPredefinedCategories,
    val insertCustomCategories: InsertCustomCategories,
    val deleteCustomCategories: DeleteCustomCategories,
)