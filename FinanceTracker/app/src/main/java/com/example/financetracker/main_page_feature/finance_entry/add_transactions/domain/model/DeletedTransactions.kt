package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model

import com.example.financetracker.data.local.data_source.room.modules.transactions.DeletedTransactionsEntity


data class DeletedTransactions(
    val transactionId: Int,
    val userUid: String
)

fun DeletedTransactions.toEntity(): DeletedTransactionsEntity {
    return DeletedTransactionsEntity(
        userUid = this.userUid,
        transactionId = transactionId
    )
}

fun DeletedTransactionsEntity.toDomain(): DeletedTransactions {
    return DeletedTransactions(
        transactionId = this.transactionId,
        userUid = this.userUid,
    )
}


