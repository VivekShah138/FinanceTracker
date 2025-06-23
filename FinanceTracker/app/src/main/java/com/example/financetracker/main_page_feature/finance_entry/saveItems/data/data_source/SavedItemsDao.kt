package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedItemsDao {

    @Upsert
    suspend fun insertSavedItems(savedItemsEntity: SavedItemsEntity)

    @Insert
    suspend fun insertSavedItemReturningId(savedItemsEntity: SavedItemsEntity): Long

    @Query("SELECT * FROM SAVEDITEMSENTITY WHERE userUID = :userUID")
    fun getAllSavedItems(userUID: String): Flow<List<SavedItemsEntity>>

    @Query("SELECT * FROM SAVEDITEMSENTITY WHERE userUID = :userUID AND cloudSync == false")
    fun getAllNotSyncedSavedItems(userUID: String): Flow<List<SavedItemsEntity>>

    @Query("SELECT * FROM SAVEDITEMSENTITY WHERE itemId = :itemId")
    fun getSavedItemById(itemId: Int): SavedItemsEntity

    @Query("DELETE FROM SavedItemsEntity WHERE itemId =:savedItemsId")
    suspend fun deleteSelectedSavedItemsByIds(savedItemsId: Int)

    @Query("UPDATE SavedItemsEntity SET cloudSync = :syncStatus WHERE itemId = :id")
    suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM SavedItemsEntity WHERE itemId = :itemId AND userUid = :userId LIMIT 1)")
    suspend fun doesTransactionExist(userId: String, itemId: Int): Boolean
}