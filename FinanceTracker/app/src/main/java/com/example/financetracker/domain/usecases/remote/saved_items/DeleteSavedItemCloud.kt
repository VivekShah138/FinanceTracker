package com.example.financetracker.domain.usecases.remote.saved_items

import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository

class DeleteSavedItemCloud(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository
) {

    suspend operator fun invoke(userId: String,itemId: Int){
        savedItemsRemoteRepository.deletedSingleSavedItemRemote (itemId = itemId,userId = userId)
    }
}