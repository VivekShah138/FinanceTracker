package com.example.financetracker.main_page_feature.view_transactions.presentation.components

import com.example.financetracker.main_page_feature.add_transactions.domain.model.Transactions

data class ViewTransactionsStates(
    val transactionsList: List<Transactions> = emptyList()
)
