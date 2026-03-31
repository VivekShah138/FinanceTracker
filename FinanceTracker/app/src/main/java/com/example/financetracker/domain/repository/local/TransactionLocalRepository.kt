package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.Transactions
import kotlinx.coroutines.flow.Flow

interface TransactionLocalRepository {

    suspend fun insertTransaction(transactions: Transactions)
    suspend fun insertTransactionAndReturnId(transactions: Transactions): Long
    suspend fun getAllTransactionsByUID(uid: String): Flow<List<Transactions>>
    suspend fun getAllUnsyncedTransactionsByUID(uid: String): Flow<List<Transactions>>
    suspend fun getTransactionById(transactionId: Int): Transactions
    suspend fun deleteTransactionsById(transactionId: Int)
    suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean)
    suspend fun doesTransactionExist(userId: String, transactionId: Int): Boolean

}