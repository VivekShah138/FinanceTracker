package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.repository.local.SavedItemsLocalRepository

class DoesItemExistsUseCase(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(userId: String,itemId: Int): Boolean{
        return savedItemsLocalRepository.doesTransactionExist(itemId = itemId, userId = userId)
    }
}