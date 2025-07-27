package com.example.financetracker.domain.usecases.remote.saved_items

import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository

class GetRemoteSavedItemList(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository,
) {

    suspend operator fun invoke(userId: String): List<SavedItems>{
        return savedItemsRemoteRepository.getRemoteSavedItems(userId = userId)
    }
}