package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository

class GetSavedItemById(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(itemId: Int): SavedItems {
        return savedItemsLocalRepository.getSavedItemById(itemId)
    }
}