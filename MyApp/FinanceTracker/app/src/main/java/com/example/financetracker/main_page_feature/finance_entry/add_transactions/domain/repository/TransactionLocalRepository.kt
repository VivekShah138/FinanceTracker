package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import kotlinx.coroutines.flow.Flow

interface TransactionLocalRepository {

    suspend fun insertTransaction(transactions: Transactions)
    suspend fun insertTransactionReturningId(transactions: Transactions): Long
    suspend fun getAllTransactions(uid: String): Flow<List<Transactions>>
    suspend fun getAllLocalTransactions(uid: String): Flow<List<Transactions>>
    suspend fun getAllLocalTransactionsById(transactionId: Int): Transactions
    suspend fun deleteSelectedTransactionsByIds(transactionId: Int)
    suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean)
    suspend fun doesTransactionExist(userId: String, transactionId: Int): Boolean

}