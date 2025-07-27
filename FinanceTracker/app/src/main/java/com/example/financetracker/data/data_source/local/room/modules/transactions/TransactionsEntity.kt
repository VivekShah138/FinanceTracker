package com.example.financetracker.data.data_source.local.room.modules.transactions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionsEntity(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int,
    val transactionName:String,
    val amount: Double,
    val currency: String?,
    val convertedAmount: Double?,
    val exchangeRate: Double?,
    val transactionType: String,
    val category: String,
    val dateTime: Long,
    val userUid: String,
    val description: String?,
    val isRecurring: Boolean,
    val cloudSync: Boolean,

)
