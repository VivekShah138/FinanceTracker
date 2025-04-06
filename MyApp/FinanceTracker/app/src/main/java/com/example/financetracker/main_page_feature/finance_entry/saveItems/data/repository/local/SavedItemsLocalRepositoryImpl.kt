package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.repository.local

import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.local.SavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.toDomain
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.toEntity
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedItemsLocalRepositoryImpl(
    private val savedItemsDao: SavedItemsDao
): SavedItemsLocalRepository {
    override suspend fun insertSavedItems(savedItems: SavedItems) {
        return savedItemsDao.insertSavedItems(savedItems.toEntity())
    }

    override suspend fun getAllSavedItems(userUID: String): Flow<List<SavedItems>> {
        return savedItemsDao.getAllSavedItems(userUID).map { savedItemList ->
            savedItemList.map { savedItem ->
                savedItem.toDomain()
            }
        }
    }
}