package com.example.financetracker.domain.repository.remote

import com.example.financetracker.domain.model.DeletedTransactions
import kotlinx.coroutines.flow.Flow

interface TransactionRemoteRepository {

    suspend fun insertDeletedTransaction(deletedTransactions: DeletedTransactions)
    suspend fun getAllDeletedTransactions(uid: String): Flow<List<DeletedTransactions>>
    suspend fun deleteSelectedDeletedTransactionsByIds(transactionId: Int)
    suspend fun deleteMultipleTransactionsFromCloud()

}