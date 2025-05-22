package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.repository.local

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.DeletedSavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.SavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.DeletedSavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.toDomain
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.toEntity
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedItemsLocalRepositoryImpl(
    private val savedItemsDao: SavedItemsDao,
    private val deletedSavedItemsDao: DeletedSavedItemsDao
): SavedItemsLocalRepository {
    override suspend fun insertSavedItems(savedItems: SavedItems) {
        return savedItemsDao.insertSavedItems(savedItems.toEntity())
    }

    override suspend fun insertNewSavedItemReturnId(savedItems: SavedItems): Long {
        return savedItemsDao.insertSavedItemReturningId(savedItemsEntity = savedItems.toEntity())
    }

    override suspend fun getAllSavedItems(userUID: String): Flow<List<SavedItems>> {
        return savedItemsDao.getAllSavedItems(userUID).map { savedItemList ->
            savedItemList.map { savedItem ->
                savedItem.toDomain()
            }
        }
    }

    override suspend fun getAllNotSyncedSavedItems(userUID: String): Flow<List<SavedItems>> {
        return savedItemsDao.getAllNotSyncedSavedItems(userUID).map { savedItemList ->
            savedItemList.map { savedItem ->
                savedItem.toDomain()
            }
        }
    }

    override suspend fun getSavedItemById(itemId: Int): SavedItems {
        return savedItemsDao.getSavedItemById(itemId).toDomain()
    }

    override suspend fun deleteSelectedSavedItemsByIds(savedItemsId: Int) {
        return savedItemsDao.deleteSelectedSavedItemsByIds(savedItemsId)
    }


    override suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean) {
        return savedItemsDao.updateCloudSyncStatus(id = id,syncStatus = syncStatus)
    }

    override suspend fun insertDeletedSavedItem(deletedSavedItems: DeletedSavedItems) {
        return deletedSavedItemsDao.insertDeletedSavedItems(deletedSavedItemsEntity = deletedSavedItems.toEntity())
    }

    override suspend fun getAllDeletedSavedItems(userUID: String): Flow<List<DeletedSavedItems>> {
        return deletedSavedItemsDao.getAllDeletedSavedItems(userUID).map { deletedSavedItems ->
            deletedSavedItems.map { deletedSavedItem ->
                deletedSavedItem.toDomain()
            }
        }
    }

    override suspend fun deleteDeletedSavedItemsById(itemId: Int) {
        return deletedSavedItemsDao.deleteDeletedSavedItemsByIds(itemId)
    }

    override suspend fun doesTransactionExist(userId: String, itemId: Int): Boolean {
        return savedItemsDao.doesTransactionExist(userId = userId,itemId = itemId)
    }
}