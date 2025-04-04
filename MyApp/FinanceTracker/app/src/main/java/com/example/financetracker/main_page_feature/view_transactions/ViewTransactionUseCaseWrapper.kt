package com.example.financetracker.main_page_feature.view_transactions

import com.example.financetracker.main_page_feature.add_transactions.domain.use_cases.GetTransactionsLocally

data class ViewTransactionUseCaseWrapper (
    val getTransactionsLocally: GetTransactionsLocally
)