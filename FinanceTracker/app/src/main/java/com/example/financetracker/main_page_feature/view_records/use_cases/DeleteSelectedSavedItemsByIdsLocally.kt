package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository

class DeleteSelectedSavedItemsByIdsLocally(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
){

    suspend operator fun invoke(savedItemsId: Int){
        return savedItemsLocalRepository.deleteSelectedSavedItemsByIds(savedItemsId)
    }
}