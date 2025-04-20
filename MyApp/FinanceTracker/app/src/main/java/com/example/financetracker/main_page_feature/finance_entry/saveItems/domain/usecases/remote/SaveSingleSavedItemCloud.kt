package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository

class SaveSingleSavedItemCloud(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository,
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {

    suspend operator fun invoke(userId: String,savedItems: SavedItems){
        savedItemsRemoteRepository.cloudSyncSingleSavedItem(
            userId = userId,
            savedItems = savedItems,
            updateCloudSync = {id,status ->
                savedItemsLocalRepository.updateCloudSyncStatus(id,status)
            }
        )
    }
}