package com.example.financetracker.mapper

import com.example.financetracker.data.data_source.local.room.modules.transactions.DeletedTransactionsEntity
import com.example.financetracker.domain.model.DeletedTransactions

object DeletedTransactionMapper{

    fun toEntity(deletedTransaction: DeletedTransactions): DeletedTransactionsEntity {
        return DeletedTransactionsEntity(
            userUid = deletedTransaction.userUid,
            transactionId = deletedTransaction.transactionId
        )
    }

    fun fromEntity(entity: DeletedTransactionsEntity): DeletedTransactions {
        return DeletedTransactions(
            transactionId = entity.transactionId,
            userUid = entity.userUid
        )
    }

}