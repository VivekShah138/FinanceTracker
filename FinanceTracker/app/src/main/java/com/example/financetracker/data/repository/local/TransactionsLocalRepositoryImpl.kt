package com.example.financetracker.data.repository.local

import com.example.financetracker.data.data_source.local.room.modules.transactions.TransactionDao
import com.example.financetracker.data.data_source.local.room.modules.transactions.TransactionsEntity
import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.domain.repository.local.TransactionLocalRepository
import com.example.financetracker.mapper.TransactionMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionsLocalRepositoryImpl(
    private val transactionDao: TransactionDao
): TransactionLocalRepository {
    override suspend fun insertTransaction(transactions: Transactions) {
        transactionDao.insertTransaction(
            transactionsEntity = TransactionMapper.toEntity(transactions)
        )
    }

    override suspend fun insertTransactionReturningId(transactions: Transactions): Long {
        return transactionDao.insertTransactionReturningId(
            transactionsEntity = TransactionMapper.toEntity(transactions)
        )
    }

    override suspend fun getAllTransactions(uid: String): Flow<List<Transactions>> {
        return transactionDao.getAllTransactions(uid).map { transactions ->
            transactions.map {
                TransactionMapper.fromEntity(it)
            }
        }
    }

    override suspend fun getAllLocalTransactions(uid: String): Flow<List<Transactions>> {
        return transactionDao.getAllLocalTransactions(uid).map {transactions ->
            transactions.map {
                TransactionMapper.fromEntity(it)
            }
        }
    }

    override suspend fun getAllLocalTransactionsById(transactionId: Int): Transactions {
        val transactionEntity = transactionDao.getAllTransactionsById(transactionId)
        return TransactionMapper.fromEntity(transactionEntity)
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