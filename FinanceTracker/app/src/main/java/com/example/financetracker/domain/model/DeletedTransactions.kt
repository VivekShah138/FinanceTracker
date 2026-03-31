package com.example.financetracker.domain.model

data class DeletedTransactions(
    val transactionId: Int,
    val userUid: String
)
