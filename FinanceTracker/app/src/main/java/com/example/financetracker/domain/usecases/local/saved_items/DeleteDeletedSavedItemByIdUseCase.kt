package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository

class DeleteDeletedSavedItemByIdUseCase(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(itemId: Int){
        return savedItemsLocalRepository.deleteDeletedSavedItemsById(itemId)
    }
}