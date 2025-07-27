package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotSyncedSavedItemUseCase(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(userUID: String): Flow<List<SavedItems>>{
        return savedItemsLocalRepository.getAllNotSyncedSavedItems(userUID)
    }
}