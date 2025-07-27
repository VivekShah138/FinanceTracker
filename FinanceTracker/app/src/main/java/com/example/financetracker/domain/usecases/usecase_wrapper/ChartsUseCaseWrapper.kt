package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.category.GetAllCategories
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalDb
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsFilters

data class ChartsUseCaseWrapper(
    val getUIDLocally: GetUIDLocally,
    val getAllTransactionsFilters: GetAllTransactionsFilters,
    val getAllCategories: GetAllCategories,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb
)
