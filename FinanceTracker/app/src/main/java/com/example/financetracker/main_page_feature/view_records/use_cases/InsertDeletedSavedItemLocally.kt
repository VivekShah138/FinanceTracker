package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.DeletedSavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository

class InsertDeletedSavedItemLocally(
    private val savedItemsLocalRepository: SavedItemsLocalRepository
){

    suspend operator fun invoke(deletedSavedItems: DeletedSavedItems){
        return savedItemsLocalRepository.insertDeletedSavedItem(deletedSavedItems = deletedSavedItems)
    }
}