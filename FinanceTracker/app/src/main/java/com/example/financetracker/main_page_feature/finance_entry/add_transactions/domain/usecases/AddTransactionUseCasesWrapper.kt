package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.remote.transaction.SaveSingleTransactionCloud
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocally
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