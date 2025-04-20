package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.remote

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository

class SaveMultipleSavedItemCloud(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository,
) {

    suspend operator fun invoke(){
        savedItemsRemoteRepository.cloudSyncMultipleSavedItems()
    }
}