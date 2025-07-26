package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote

import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository

class GetRemoteSavedItemList(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository,
) {

    suspend operator fun invoke(userId: String): List<SavedItems>{
        return savedItemsRemoteRepository.getRemoteSavedItems(userId = userId)
    }
}