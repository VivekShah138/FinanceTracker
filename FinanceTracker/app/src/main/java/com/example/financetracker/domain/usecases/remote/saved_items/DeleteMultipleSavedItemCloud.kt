package com.example.financetracker.domain.usecases.remote.saved_items

import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository

class DeleteMultipleSavedItemCloud(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository,
) {

    suspend operator fun invoke(){
        savedItemsRemoteRepository.deleteMultipleSavedItemsFromCloud()
    }
}