package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.category.DeleteCustomCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.GetAllCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.GetCustomCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.GetPredefinedCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.InsertCustomCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.category.SeedPredefinedCategoriesLocalUseCase

data class PredefinedCategoriesUseCaseWrapper(
    val getAllCategoriesLocalUseCase: GetAllCategoriesLocalUseCase,
    val seedPredefinedCategoriesLocalUseCase: SeedPredefinedCategoriesLocalUseCase,
    val getCustomCategoriesLocalUseCase: GetCustomCategoriesLocalUseCase,
    val getPredefinedCategoriesLocalUseCase: GetPredefinedCategoriesLocalUseCase,
    val insertCustomCategoriesLocalUseCase: InsertCustomCategoriesLocalUseCase,
    val deleteCustomCategoriesLocalUseCase: DeleteCustomCategoriesLocalUseCase,
)