package com.example.financetracker.main_page_feature.view_records.transactions.utils

sealed class TransactionOrder (val label: String){
    data object Ascending: TransactionOrder(label = "Ascending")
    data object Descending: TransactionOrder(label = "Descending")
}
