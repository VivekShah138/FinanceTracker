package com.example.financetracker.main_page_feature.add_transactions.expense.domain.usecases

import com.example.financetracker.main_page_feature.add_transactions.domain.use_cases.InsertTransactionsLocally
import com.example.financetracker.setup_account.domain.usecases.GetCurrencyRatesLocally

data class AddExpenseUseCasesWrapper (
    val getCurrencyRatesLocally: GetCurrencyRatesLocally,
    val insertCustomCategory: InsertCustomCategory,
    val validateTransactionName: ValidateTransactionName,
    val validateTransactionCategory: ValidateTransactionCategory,
    val validateTransactionPrice: ValidateTransactionPrice,
    val insertTransactionsLocally: InsertTransactionsLocally
)