package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.DeletedSavedItems
import com.example.financetracker.domain.model.SavedItems
import kotlinx.coroutines.flow.Flow

interface SavedItemsLocalRepository {
    suspend fun insertSavedItems(savedItems: SavedItems)
    suspend fun insertSavedItemAndReturnId(savedItems: SavedItems): Long
    suspend fun getAllSavedItems(userUID: String): Flow<List<SavedItems>>
    suspend fun getAllUnSyncedSavedItems(userUID: String): Flow<List<SavedItems>>
    suspend fun getSavedItemById(itemId: Int): SavedItems
    suspend fun deleteSavedItemsById(savedItemsId: Int)
    suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean)
    suspend fun insertDeletedSavedItem(deletedSavedItems: DeletedSavedItems)
    suspend fun getAllDeletedSavedItems(userUID: String): Flow<List<DeletedSavedItems>>
    suspend fun deleteDeletedSavedItemsById(itemId: Int)
    suspend fun doesSavedItemExist(userId: String, itemId: Int): Boolean

}