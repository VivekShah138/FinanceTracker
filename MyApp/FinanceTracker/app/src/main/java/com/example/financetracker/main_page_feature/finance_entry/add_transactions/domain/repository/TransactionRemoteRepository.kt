package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionsEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import kotlinx.coroutines.flow.Flow

interface TransactionRemoteRepository {

    suspend fun insertDeletedTransaction(deletedTransactions: DeletedTransactions)
    suspend fun getAllDeletedTransactions(uid: String): Flow<List<DeletedTransactionsEntity>>
    suspend fun deleteSelectedDeletedTransactionsByIds(transactionIds: Set<Int>)
    suspend fun deleteAllDeletedTransactions()

}