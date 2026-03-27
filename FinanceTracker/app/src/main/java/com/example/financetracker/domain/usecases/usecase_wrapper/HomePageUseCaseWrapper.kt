package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.budget.GetBudgetLocalUseCase
import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationLocalUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.local.category.GetAllCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetCurrencyRatesUpdatedLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetCurrencyRatesUpdatedLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsByUIDLocalUseCase
import com.example.financetracker.domain.usecases.remote.saved_items.InsertRemoteSavedItemToLocal
import com.example.financetracker.domain.usecases.local.transaction.GetAllTransactionsFiltersUseCase
import com.example.financetracker.domain.usecases.local.user_profile.GetUserProfileLocalUseCase

data class HomePageUseCaseWrapper (
    val logoutUseCase: LogoutUseCase,
    val getUserProfileLocalUseCase: GetUserProfileLocalUseCase,
    val setCurrencyRatesUpdatedLocalUseCase: SetCurrencyRatesUpdatedLocalUseCase,
    val getCurrencyRatesUpdatedLocalUseCase: GetCurrencyRatesUpdatedLocalUseCase,
    val getAllTransactionsByUIDLocalUseCase: GetAllTransactionsByUIDLocalUseCase,
    val getAllTransactionsFilters: GetAllTransactionsFiltersUseCase,
    val getUIDLocalUseCase: GetUIDLocalUseCase,
    val getAllCategoriesLocalUseCase: GetAllCategoriesLocalUseCase,
    val getBudgetLocalUseCase: GetBudgetLocalUseCase,
    val sendBudgetNotificationLocalUseCase: SendBudgetNotificationLocalUseCase,
    val insertRemoteSavedItemToLocal: InsertRemoteSavedItemToLocal
)
