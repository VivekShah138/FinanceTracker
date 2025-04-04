package com.example.financetracker.main_page_feature.add_transactions.domain.repository

import com.example.financetracker.main_page_feature.add_transactions.domain.model.Transactions
import kotlinx.coroutines.flow.Flow

interface TransactionLocalRepository {

    suspend fun insertTransaction(transactions: Transactions)
    suspend fun getAllTransactions(uid: String): Flow<List<Transactions>>

}