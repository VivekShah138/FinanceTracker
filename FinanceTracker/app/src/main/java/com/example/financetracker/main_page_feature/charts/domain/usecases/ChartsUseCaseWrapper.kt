package com.example.financetracker.main_page_feature.charts.domain.usecases

import com.example.financetracker.domain.usecases.local.category.GetAllCategories
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileFromLocalDb
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.main_page_feature.view_records.use_cases.GetAllTransactionsFilters

data class ChartsUseCaseWrapper(
    val getUIDLocally: GetUIDLocally,
    val getAllTransactionsFilters: GetAllTransactionsFilters,
    val getAllCategories: GetAllCategories,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb
)
