package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository

class DeleteSavedItemCloud(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository
) {

    suspend operator fun invoke(userId: String,itemId: Int){
        savedItemsRemoteRepository.deletedSingleSavedItemRemote (itemId = itemId,userId = userId)
    }
}