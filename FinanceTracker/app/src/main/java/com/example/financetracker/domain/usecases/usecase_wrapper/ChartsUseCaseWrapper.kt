package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.category.GetAllCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsFiltersUseCase

data class ChartsUseCaseWrapper(
    val getUIDLocalUseCase: GetUIDLocalUseCase,
    val getAllTransactionsFilters: GetAllTransactionsFiltersUseCase,
    val getAllCategoriesLocalUseCase: GetAllCategoriesLocalUseCase,
    val getUserProfileFromLocalUseCase: GetUserProfileFromLocalUseCase
)
