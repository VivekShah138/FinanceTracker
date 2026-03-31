package com.example.financetracker.data.repository.local

import com.example.financetracker.data.data_source.local.room.modules.transactions.TransactionDao
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

    override suspend fun insertTransactionAndReturnId(transactions: Transactions): Long {
        return transactionDao.insertTransactionReturningId(
            transactionsEntity = TransactionMapper.toEntity(transactions)
        )
    }

    override suspend fun getAllTransactionsByUID(uid: String): Flow<List<Transactions>> {
        return transactionDao.getAllTransactionsByUID(uid).map { transactions ->
            transactions.map {
                TransactionMapper.fromEntity(it)
            }
        }
    }

    override suspend fun getAllUnsyncedTransactionsByUID(uid: String): Flow<List<Transactions>> {
        return transactionDao.getAllUnsyncedTransactionsByUID(uid).map { transactions ->
            transactions.map {
                TransactionMapper.fromEntity(it)
            }
        }
    }

    override suspend fun getTransactionById(transactionId: Int): Transactions {
        val transactionEntity = transactionDao.getTransactionById(transactionId)
        return TransactionMapper.fromEntity(transactionEntity)
    }

    override suspend fun deleteTransactionsById(transactionId: Int) {
        return transactionDao.deleteTransactionById(transactionId)
    }

    override suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean) {
        return transactionDao.updateCloudSyncStatus(id = id,syncStatus = syncStatus)
    }

    override suspend fun doesTransactionExist(userId: String, transactionId: Int): Boolean {
        return transactionDao.doesTransactionExist(userId = userId,transactionId = transactionId)
    }
}