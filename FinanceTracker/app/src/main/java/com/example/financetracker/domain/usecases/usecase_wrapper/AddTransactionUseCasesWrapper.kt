package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.budget.SendBudgetNotificationLocalUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.InternetConnectionAvailability
import com.example.financetracker.domain.usecases.remote.transactions.InsertSingleTransactionRemoteUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetCloudSyncLocalUseCase
import com.example.financetracker.domain.usecases.local.currency_rates.GetCurrencyRatesLocal
import com.example.financetracker.domain.usecases.local.transaction.DoesTransactionExitsLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.GetAllUnsyncedTransactionsLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.GetAllTransactionsRemoteUseCase
import com.example.financetracker.domain.usecases.local.category.InsertCustomCategoryLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.InsertTransactionsAndReturnIdLocalUseCase
import com.example.financetracker.domain.usecases.remote.transactions.SyncTransactionsRemoteToLocalUseCase
import com.example.financetracker.domain.usecases.local.transaction.InsertTransactionsLocalUseCase
import com.example.financetracker.domain.usecases.local.validation.EmptyFieldValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.TransactionCategoryValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.TransactionPriceValidationUseCase

data class AddTransactionUseCasesWrapper (
    val getCurrencyRatesLocal: GetCurrencyRatesLocal,
    val insertCustomCategoryLocalUseCase: InsertCustomCategoryLocalUseCase,
    val emptyFieldValidationUseCase: EmptyFieldValidationUseCase,
    val transactionCategoryValidationUseCase: TransactionCategoryValidationUseCase,
    val transactionPriceValidationUseCase: TransactionPriceValidationUseCase,
    val insertTransactionsLocalUseCase: InsertTransactionsLocalUseCase,
    val insertTransactionsAndReturnIdLocalUseCase: InsertTransactionsAndReturnIdLocalUseCase,
    val insertSingleTransactionRemoteUseCase: InsertSingleTransactionRemoteUseCase,
    val getCloudSyncLocalUseCase: GetCloudSyncLocalUseCase,
    val internetConnectionAvailability: InternetConnectionAvailability,
    val getAllUnsyncedTransactionsLocalUseCase: GetAllUnsyncedTransactionsLocalUseCase,
    val sendBudgetNotificationLocalUseCase: SendBudgetNotificationLocalUseCase,
    val doesTransactionExitsLocalUseCase: DoesTransactionExitsLocalUseCase,
    val getAllTransactionsRemoteUseCase: GetAllTransactionsRemoteUseCase,
    val getUserUIDRemoteUseCase: GetUserUIDRemoteUseCase,
    val syncTransactionsRemoteToLocalUseCase: SyncTransactionsRemoteToLocalUseCase

)