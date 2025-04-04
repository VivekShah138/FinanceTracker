package com.example.financetracker.main_page_feature.add_transactions.data.local.repository

import com.example.financetracker.main_page_feature.add_transactions.data.local.data_source.TransactionDao
import com.example.financetracker.main_page_feature.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.add_transactions.domain.model.toDomain
import com.example.financetracker.main_page_feature.add_transactions.domain.model.toEntity
import com.example.financetracker.main_page_feature.add_transactions.domain.repository.TransactionLocalRepository
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

    override suspend fun getAllTransactions(uid: String): Flow<List<Transactions>> {
        return transactionDao.getAllTransactions(uid).map { transactions ->
            transactions.map {
                it.toDomain()
            }
        }
    }
}