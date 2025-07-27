package com.example.financetracker.main_page_feature.home_page.domain.usecases

import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.local.category.GetAllCategories
import com.example.financetracker.domain.usecases.local.shared_pref.GetCurrencyRatesUpdated
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocally
import com.example.financetracker.domain.usecases.local.shared_pref.SetCurrencyRatesUpdated
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetAllTransactions
import com.example.financetracker.domain.usecases.remote.saved_items.InsertRemoteSavedItemToLocal
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsFilters

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
