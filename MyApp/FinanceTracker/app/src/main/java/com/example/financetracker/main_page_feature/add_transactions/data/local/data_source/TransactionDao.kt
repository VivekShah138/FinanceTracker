package com.example.financetracker.main_page_feature.add_transactions.data.local.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.financetracker.main_page_feature.add_transactions.domain.model.Transactions
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(transactionsEntity: TransactionsEntity)

    @Query("SELECT * FROM TransactionsEntity")
    fun getAllTransactions(): Flow<List<TransactionsEntity>>

}