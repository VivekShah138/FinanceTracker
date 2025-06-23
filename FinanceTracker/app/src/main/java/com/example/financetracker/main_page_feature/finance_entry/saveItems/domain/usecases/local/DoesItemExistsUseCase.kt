package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local

import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository

class DoesItemExistsUseCase(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(userId: String,itemId: Int): Boolean{
        return savedItemsLocalRepository.doesTransactionExist(itemId = itemId, userId = userId)
    }
}