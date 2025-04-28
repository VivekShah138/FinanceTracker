package com.example.financetracker.main_page_feature.view_records.transactions.utils

sealed class TransactionOrder {
    data object Ascending: TransactionOrder()
    data object Descending: TransactionOrder()
}
