package com.example.financetracker.utils

sealed class TransactionOrder (val label: String){
    data object Ascending: TransactionOrder(label = "Ascending")
    data object Descending: TransactionOrder(label = "Descending")
}
