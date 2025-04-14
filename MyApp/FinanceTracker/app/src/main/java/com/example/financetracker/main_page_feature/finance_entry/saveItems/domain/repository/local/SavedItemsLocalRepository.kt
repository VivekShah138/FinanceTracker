package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import kotlinx.coroutines.flow.Flow

interface SavedItemsLocalRepository {

    suspend fun insertSavedItems(savedItems: SavedItems)
    suspend fun getAllSavedItems(userUID: String): Flow<List<SavedItems>>
    suspend fun deleteSelectedSavedItemsByIds(savedItemsIds: Set<Int>)

}