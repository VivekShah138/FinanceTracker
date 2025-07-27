package com.example.financetracker.domain.usecases.remote.saved_items

import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository

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