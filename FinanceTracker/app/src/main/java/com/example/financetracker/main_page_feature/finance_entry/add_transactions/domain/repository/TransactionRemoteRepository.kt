package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import kotlinx.coroutines.flow.Flow

interface TransactionRemoteRepository {

    suspend fun insertDeletedTransaction(deletedTransactions: DeletedTransactions)
    suspend fun getAllDeletedTransactions(uid: String): Flow<List<DeletedTransactions>>
    suspend fun deleteSelectedDeletedTransactionsByIds(transactionId: Int)
    suspend fun deleteMultipleTransactionsFromCloud()

}