package com.example.financetracker.main_page_feature.view_transactions

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.GetTransactionsLocally

data class ViewTransactionUseCaseWrapper (
    val getTransactionsLocally: GetTransactionsLocally
)