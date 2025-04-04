package com.example.financetracker.main_page_feature.add_transactions.domain.model

import com.example.financetracker.main_page_feature.add_transactions.data.local.data_source.TransactionsEntity
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.domain.model.Currency
import kotlin.concurrent.thread


data class Transactions(
    val transactionId: Int? = null,
    val amount: Double,
    val currency: Map<String,Currency>?,
    val convertedAmount: Double?,
    val exchangeRate: Double?,
    val transactionType: String,
    val category: String,
    val dateTime: Long,
    val userUid: String,
    val description: String?,
    val isRecurring: Boolean,
    val cloudSync: Boolean,
    val transactionName: String
)

fun Transactions.toEntity(): TransactionsEntity {
    return TransactionsEntity(
        transactionId = 0,
        amount = this.amount,
        currency = CountryMapper.fromCurrencies(this.currency),  // Assuming you want the first currency's key for storage
        convertedAmount = this.convertedAmount,
        exchangeRate = this.exchangeRate,
        transactionType = this.transactionType,
        category = this.category,
        dateTime = this.dateTime,
        userUid = this.userUid,
        description = this.description,
        isRecurring = this.isRecurring,
        cloudSync = this.cloudSync,
        transactionName = this.transactionName
    )
}

fun TransactionsEntity.toDomain(): Transactions {
    return Transactions(
        transactionId = this.transactionId,
        amount = this.amount,
        currency = CountryMapper.toCurrencies(this.currency),  // Assuming you have the currency map available here
        convertedAmount = this.convertedAmount,
        exchangeRate = this.exchangeRate,
        transactionType = this.transactionType,
        category = this.category,
        dateTime = this.dateTime,
        userUid = this.userUid,
        description = this.description,
        isRecurring = this.isRecurring,
        cloudSync = this.cloudSync,
        transactionName = this.transactionName
    )
}


