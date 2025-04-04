package com.example.financetracker.main_page_feature.add_transactions.data.local.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.financetracker.setup_account.domain.model.Currency

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
