package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.repository

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.toDomain
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.toEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionsLocalRepositoryImpl(
    private val transactionDao: TransactionDao
): TransactionLocalRepository {
    override suspend fun insertTransaction(transactions: Transactions) {
        transactionDao.insertTransaction(
            transactionsEntity = transactions.toEntity()
        )
    }

    override suspend fun insertTransactionReturningId(transactions: Transactions): Long {
        return transactionDao.insertTransactionReturningId(
            transactionsEntity = transactions.toEntity()
        )
    }

    override suspend fun getAllTransactions(uid: String): Flow<List<Transactions>> {
        return transactionDao.getAllTransactions(uid).map { transactions ->
            transactions.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getAllLocalTransactions(uid: String): Flow<List<Transactions>> {
        return transactionDao.getAllLocalTransactions(uid).map {transactions ->
            transactions.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getAllLocalTransactionsById(transactionId: Int): Transactions {
        return transactionDao.getAllTransactionsById(transactionId).toDomain()
    }

    override suspend fun deleteSelectedTransactionsByIds(transactionId: Int) {
        return transactionDao.deleteSelectedTransactionsByIds(transactionId)
    }

    override suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean) {
        return transactionDao.updateCloudSyncStatus(id = id,syncStatus = syncStatus)
    }

    override suspend fun doesTransactionExist(userId: String, transactionId: Int): Boolean {
        return transactionDao.doesTransactionExist(userId = userId,transactionId = transactionId)
    }
}