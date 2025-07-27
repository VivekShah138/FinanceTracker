package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.model.DeletedSavedItems
import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository

class InsertDeletedSavedItemLocally(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
){

    suspend operator fun invoke(deletedSavedItems: DeletedSavedItems){
        return savedItemsLocalRepository.insertDeletedSavedItem(deletedSavedItems = deletedSavedItems)
    }
}