package com.example.financetracker.main_page_feature.home_page.domain.usecases

import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationUseCase
import com.example.financetracker.core.core_domain.usecase.LogoutUseCase
import com.example.financetracker.core.local.domain.room.usecases.GetAllCategories
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCurrencyRatesUpdated
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllTransactions
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote.InsertRemoteSavedItemToLocal
import com.example.financetracker.main_page_feature.view_records.use_cases.GetAllTransactionsFilters

data class HomePageUseCaseWrapper (
    val logoutUseCase: LogoutUseCase,
    val getUserProfileLocal: GetUserProfileLocal,
    val setCurrencyRatesUpdated: SetCurrencyRatesUpdated,
    val getCurrencyRatesUpdated: GetCurrencyRatesUpdated,
    val getAllTransactions: GetAllTransactions,
    val getAllTransactionsFilters: GetAllTransactionsFilters,
    val getUIDLocally: GetUIDLocally,
    val getAllCategories: GetAllCategories,
    val getBudgetLocalUseCase: GetBudgetLocalUseCase,
    val sendBudgetNotificationUseCase: SendBudgetNotificationUseCase,
    val insertRemoteSavedItemToLocal: InsertRemoteSavedItemToLocal
)
