package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedTransactionsEntity(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int,
    val userUid: String,
)
