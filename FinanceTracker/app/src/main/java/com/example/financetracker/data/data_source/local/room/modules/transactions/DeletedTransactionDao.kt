package com.example.financetracker.data.data_source.local.room.modules.transactions

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DeletedTransactionDao {

    @Upsert
    suspend fun insertDeletedTransaction(deletedTransactionsEntity: DeletedTransactionsEntity)

    @Query("SELECT * FROM DeletedTransactionsEntity WHERE userUid = :uid")
    fun getAllDeletedTransactions(uid: String): Flow<List<DeletedTransactionsEntity>>

    @Query("DELETE FROM DeletedTransactionsEntity WHERE transactionId =:transactionId")
    suspend fun deleteSelectedDeletedTransactionsByIds(transactionId: Int)




}