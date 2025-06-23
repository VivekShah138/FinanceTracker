package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.repository.remote.SavedItemsRemoteRepositoryImpl
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository

class DeleteSavedItemCloud(
    private val savedItemsRemoteRepository: SavedItemsRemoteRepository
) {

    suspend operator fun invoke(userId: String,itemId: Int){
        savedItemsRemoteRepository.deletedSingleSavedItemRemote (itemId = itemId,userId = userId)
    }
}