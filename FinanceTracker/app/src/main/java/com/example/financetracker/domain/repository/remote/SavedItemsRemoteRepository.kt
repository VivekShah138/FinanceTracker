package com.example.financetracker.domain.repository.remote

import com.example.financetracker.domain.model.DeletedSavedItems
import com.example.financetracker.domain.model.SavedItems
import kotlinx.coroutines.flow.Flow

interface SavedItemsRemoteRepository {

    suspend fun insertDeletedSavedItems(deletedSavedItems: DeletedSavedItems)
    suspend fun getAllDeletedSavedItems(uid: String): Flow<List<DeletedSavedItems>>
    suspend fun deleteDeletedSavedItemById(itemId: Int)
    suspend fun deleteMultipleSavedItemsFromCloud()
    suspend fun deleteSavedItemRemote(itemId: Int, userId: String)
    suspend fun syncSavedItem(userId: String, savedItems: SavedItems, updateCloudSync:suspend (Int, Boolean) -> Unit)
    suspend fun syncMultipleSavedItemsToRemote()
    suspend fun insertSavedItemRemoteToLocal()
    suspend fun getSavedItemsRemote(userId:String): List<SavedItems>

}