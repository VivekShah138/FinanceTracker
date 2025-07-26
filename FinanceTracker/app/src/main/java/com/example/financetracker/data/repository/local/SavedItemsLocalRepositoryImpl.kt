package com.example.financetracker.data.repository.local

import com.example.financetracker.data.data_source.local.room.modules.saved_items.DeletedSavedItemsDao
import com.example.financetracker.data.data_source.local.room.modules.saved_items.SavedItemsDao
import com.example.financetracker.domain.model.DeletedSavedItems
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.mapper.DeletedSavedItemsMapper
import com.example.financetracker.mapper.SavedItemsMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedItemsLocalRepositoryImpl(
    private val savedItemsDao: SavedItemsDao,
    private val deletedSavedItemsDao: DeletedSavedItemsDao
): SavedItemsLocalRepository {
    override suspend fun insertSavedItems(savedItems: SavedItems) {
        return savedItemsDao.insertSavedItems(SavedItemsMapper.toEntity(savedItems))
    }

    override suspend fun insertNewSavedItemReturnId(savedItems: SavedItems): Long {
        return savedItemsDao.insertSavedItemReturningId(savedItemsEntity = SavedItemsMapper.toEntity(savedItems))
    }

    override suspend fun getAllSavedItems(userUID: String): Flow<List<SavedItems>> {
        return savedItemsDao.getAllSavedItems(userUID).map { savedItemList ->
            savedItemList.map { savedItem ->
                SavedItemsMapper.toDomain(savedItem)
            }
        }
    }

    override suspend fun getAllNotSyncedSavedItems(userUID: String): Flow<List<SavedItems>> {
        return savedItemsDao.getAllNotSyncedSavedItems(userUID).map { savedItemList ->
            savedItemList.map { savedItem ->
                SavedItemsMapper.toDomain(savedItem)
            }
        }
    }

    override suspend fun getSavedItemById(itemId: Int): SavedItems {
        val savedItemEntity = savedItemsDao.getSavedItemById(itemId)
        return SavedItemsMapper.toDomain(savedItemEntity)
    }

    override suspend fun deleteSelectedSavedItemsByIds(savedItemsId: Int) {
        return savedItemsDao.deleteSelectedSavedItemsByIds(savedItemsId)
    }


    override suspend fun updateCloudSyncStatus(id: Int, syncStatus: Boolean) {
        return savedItemsDao.updateCloudSyncStatus(id = id,syncStatus = syncStatus)
    }

    override suspend fun insertDeletedSavedItem(deletedSavedItems: DeletedSavedItems) {
        return deletedSavedItemsDao.insertDeletedSavedItems(deletedSavedItemsEntity = DeletedSavedItemsMapper.toEntity(deletedSavedItems))
    }

    override suspend fun getAllDeletedSavedItems(userUID: String): Flow<List<DeletedSavedItems>> {
        return deletedSavedItemsDao.getAllDeletedSavedItems(userUID).map { deletedSavedItems ->
            deletedSavedItems.map { deletedSavedItem ->
                DeletedSavedItemsMapper.toDomain(deletedSavedItem)
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