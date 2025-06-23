package com.example.financetracker.main_page_feature.charts.domain.usecases

import com.example.financetracker.core.local.domain.room.usecases.GetAllCategories
import com.example.financetracker.core.local.domain.room.usecases.GetUserProfileFromLocalDb
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllTransactions
import com.example.financetracker.main_page_feature.view_records.use_cases.GetAllTransactionsFilters

data class ChartsUseCaseWrapper(
    val getUIDLocally: GetUIDLocally,
    val getAllTransactionsFilters: GetAllTransactionsFilters,
    val getAllCategories: GetAllCategories,
    val getUserProfileFromLocalDb: GetUserProfileFromLocalDb
)
