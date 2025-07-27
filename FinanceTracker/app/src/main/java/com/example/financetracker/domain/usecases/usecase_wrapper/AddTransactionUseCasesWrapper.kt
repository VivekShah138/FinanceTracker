package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.remote.transactions.SaveSingleTransactionCloud
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocally
import com.example.financetracker.domain.usecases.local.currency_rates.GetCurrencyRatesLocally
import com.example.financetracker.domain.usecases.local.transaction.DoesTransactionExits
import com.example.financetracker.domain.usecases.local.transaction.GetAllLocalTransactions
import com.example.financetracker.domain.usecases.remote.transactions.GetRemoteTransactionsList
import com.example.financetracker.domain.usecases.local.category.InsertCustomCategory
import com.example.financetracker.domain.usecases.local.transaction.InsertNewTransactionsReturnId
import com.example.financetracker.domain.usecases.remote.transactions.InsertRemoteTransactionsToLocal
import com.example.financetracker.domain.usecases.local.transaction.InsertTransactionsLocally
import com.example.financetracker.domain.usecases.local.validation.ValidateEmptyField
import com.example.financetracker.domain.usecases.local.validation.ValidateTransactionCategory
import com.example.financetracker.domain.usecases.local.validation.ValidateTransactionPrice

data class AddTransactionUseCasesWrapper (
    val getCurrencyRatesLocally: GetCurrencyRatesLocally,
    val insertCustomCategory: InsertCustomCategory,
    val validateEmptyField: ValidateEmptyField,
    val validateTransactionCategory: ValidateTransactionCategory,
    val validateTransactionPrice: ValidateTransactionPrice,
    val insertTransactionsLocally: InsertTransactionsLocally,
    val insertNewTransactionsReturnId: InsertNewTransactionsReturnId,
    val saveSingleTransactionCloud: SaveSingleTransactionCloud,
    val getCloudSyncLocally: GetCloudSyncLocally,
    val internetConnectionAvailability: InternetConnectionAvailability,
    val getAllLocalTransactions: GetAllLocalTransactions,
    val sendBudgetNotificationUseCase: SendBudgetNotificationUseCase,
    val doesTransactionExits: DoesTransactionExits,
    val getRemoteTransactionsList: GetRemoteTransactionsList,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val insertRemoteTransactionsToLocal: InsertRemoteTransactionsToLocal

)