package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(transactionsEntity: TransactionsEntity)

    @Insert
    suspend fun insertTransactionReturningId(transactionsEntity: TransactionsEntity): Long

    @Query("SELECT * FROM TransactionsEntity WHERE userUid = :uid")
    fun getAllTransactions(uid: String): Flow<List<TransactionsEntity>>

    @Query("DELETE FROM TransactionsEntity WHERE transactionId IN (:transactionIds)")
    suspend fun deleteSelectedTransactionsByIds(transactionIds: Set<Int>)

    @Query("UPDATE TransactionsEntity SET cloudSync = :syncStatus WHERE transactionId = :id")
    suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean)


}