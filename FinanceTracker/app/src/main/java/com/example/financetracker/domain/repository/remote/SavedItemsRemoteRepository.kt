package com.example.financetracker.domain.repository.remote

import com.example.financetracker.domain.model.DeletedSavedItems
import com.example.financetracker.domain.model.SavedItems
import kotlinx.coroutines.flow.Flow

interface SavedItemsRemoteRepository {

    suspend fun insertDeletedSavedItems(deletedSavedItems: DeletedSavedItems)
    suspend fun getAllDeletedSavedItems(uid: String): Flow<List<DeletedSavedItems>>
    suspend fun deleteSelectedDeletedTransactionsByIds(itemId: Int)
    suspend fun deleteMultipleSavedItemsFromCloud()
    suspend fun deletedSingleSavedItemRemote(itemId: Int, userId: String)
    suspend fun cloudSyncSingleSavedItem(userId: String, savedItems: SavedItems, updateCloudSync:suspend (Int, Boolean) -> Unit)
    suspend fun cloudSyncMultipleSavedItems()
    suspend fun insertRemoteItemToLocal()
    suspend fun getRemoteSavedItems(userId:String): List<SavedItems>

}