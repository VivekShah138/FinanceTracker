package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local

import com.example.financetracker.domain.model.DeletedSavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllDeletedSavedItemsByUserId(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(userId: String): Flow<List<DeletedSavedItems>>{
        return savedItemsLocalRepository.getAllDeletedSavedItems(userId)
    }
}