package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.repository

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionsEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.toDomain
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.toEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionsRemoteRepositoryImpl(
    private val deletedTransactionDao: DeletedTransactionDao
): TransactionRemoteRepository {
    override suspend fun insertDeletedTransaction(deletedTransactions: DeletedTransactions) {
        return deletedTransactionDao.insertDeletedTransaction(deletedTransactions.toEntity())
    }

    override suspend fun getAllDeletedTransactions(uid: String): Flow<List<DeletedTransactionsEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSelectedDeletedTransactionsByIds(transactionIds: Set<Int>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllDeletedTransactions() {
        TODO("Not yet implemented")
    }

}