package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.budget_feature.domain.usecases.SendBudgetNotificationUseCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserUIDUseCase
import com.example.financetracker.core.cloud.domain.usecase.InternetConnectionAvailability
import com.example.financetracker.core.cloud.domain.usecase.SaveSingleTransactionCloud
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCloudSyncLocally
import com.example.financetracker.setup_account.domain.usecases.GetCurrencyRatesLocally

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