package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository

class DeleteSelectedSavedItemsByIdsLocally(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
){

    suspend operator fun invoke(savedItemsId: Int){
        return savedItemsLocalRepository.deleteSelectedSavedItemsByIds(savedItemsId)
    }
}