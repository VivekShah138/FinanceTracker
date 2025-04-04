package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.setup_account.domain.usecases.GetCurrencyRatesLocally

data class AddTransactionUseCasesWrapper (
    val getCurrencyRatesLocally: GetCurrencyRatesLocally,
    val insertCustomCategory: InsertCustomCategory,
    val validateTransactionName: ValidateTransactionName,
    val validateTransactionCategory: ValidateTransactionCategory,
    val validateTransactionPrice: ValidateTransactionPrice,
    val insertTransactionsLocally: InsertTransactionsLocally
)