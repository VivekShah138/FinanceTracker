package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.DeletedSavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import kotlinx.coroutines.flow.Flow

interface SavedItemsLocalRepository {

    suspend fun insertSavedItems(savedItems: SavedItems)
    suspend fun insertNewSavedItemReturnId(savedItems: SavedItems): Long
    suspend fun getAllSavedItems(userUID: String): Flow<List<SavedItems>>
    suspend fun getAllNotSyncedSavedItems(userUID: String): Flow<List<SavedItems>>
    suspend fun getSavedItemById(itemId: Int): SavedItems
    suspend fun deleteSelectedSavedItemsByIds(savedItemsId: Int)
    suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean)

    // Deleted Saved Items
    suspend fun insertDeletedSavedItem(deletedSavedItems: DeletedSavedItems)
    suspend fun getAllDeletedSavedItems(userUID: String): Flow<List<DeletedSavedItems>>
    suspend fun deleteDeletedSavedItemsById(itemId: Int)

    suspend fun doesTransactionExist(userId: String, itemId: Int): Boolean

}