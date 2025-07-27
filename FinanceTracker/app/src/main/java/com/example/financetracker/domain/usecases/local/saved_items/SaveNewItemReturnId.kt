package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository

class SaveNewItemReturnId(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(savedItems: SavedItems): Long{
        return savedItemsLocalRepository.insertNewSavedItemReturnId(savedItems = savedItems)
    }
}