package com.example.financetracker.data.data_source.local.room.modules.transactions

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
    fun getAllTransactionsByUID(uid: String): Flow<List<TransactionsEntity>>

    @Query("SELECT * FROM TransactionsEntity WHERE userUid = :uid AND cloudSync == false")
    fun getAllUnsyncedTransactionsByUID(uid: String): Flow<List<TransactionsEntity>>

    @Query("SELECT * FROM TransactionsEntity WHERE transactionId = :transactionId")
    fun getTransactionById(transactionId: Int): TransactionsEntity

    @Query("DELETE FROM TransactionsEntity WHERE transactionId =:transactionId")
    suspend fun deleteTransactionById(transactionId: Int)

    @Query("UPDATE TransactionsEntity SET cloudSync = :syncStatus WHERE transactionId = :id")
    suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM TransactionsEntity WHERE transactionId = :transactionId AND userUid = :userId LIMIT 1)")
    suspend fun doesTransactionExist(userId: String, transactionId: Int): Boolean
}