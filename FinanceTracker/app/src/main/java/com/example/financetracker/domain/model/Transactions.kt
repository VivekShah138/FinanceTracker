package com.example.financetracker.domain.model


data class Transactions(
    val transactionId: Int? = null,
    val amount: Double = 0.0,
    val currency: Map<String, Currency>? = null,
    val convertedAmount: Double? = null,
    val exchangeRate: Double? = null,
    val transactionType: String = "",
    val category: String = "",
    val dateTime: Long = 0,
    val userUid: String = "",
    val description: String? = null,
    val isRecurring: Boolean = false,
    val cloudSync: Boolean = false,
    val transactionName: String = ""
)

