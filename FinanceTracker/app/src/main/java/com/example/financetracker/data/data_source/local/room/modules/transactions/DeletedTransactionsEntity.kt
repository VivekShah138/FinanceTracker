package com.example.financetracker.data.data_source.local.room.modules.transactions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedTransactionsEntity(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int,
    val userUid: String,
)
