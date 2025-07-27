package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository

class SaveItemLocalUseCase(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(savedItems: SavedItems){
        savedItemsLocalRepository.insertSavedItems(savedItems)
    }
}