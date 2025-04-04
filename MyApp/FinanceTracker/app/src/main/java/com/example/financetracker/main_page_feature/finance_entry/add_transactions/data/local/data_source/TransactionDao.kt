package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(transactionsEntity: TransactionsEntity)

    @Query("SELECT * FROM TransactionsEntity WHERE userUid = :uid")
    fun getAllTransactions(uid: String): Flow<List<TransactionsEntity>>

}