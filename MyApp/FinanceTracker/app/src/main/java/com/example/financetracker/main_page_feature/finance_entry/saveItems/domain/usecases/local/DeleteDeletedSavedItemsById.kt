package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.local

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository

class DeleteDeletedSavedItemsById(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
) {
    suspend operator fun invoke(itemId: Int){
        return savedItemsLocalRepository.deleteDeletedSavedItemsById(itemId)
    }
}