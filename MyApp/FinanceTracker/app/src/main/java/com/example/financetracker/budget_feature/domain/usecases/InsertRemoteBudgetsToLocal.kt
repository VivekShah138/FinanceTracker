package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.budget_feature.domain.repository.BudgetRemoteRepository
import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.local.SavedItemsLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository

class InsertRemoteBudgetsToLocal(
    private val budgetRemoteRepository: BudgetRemoteRepository
) {

    suspend operator fun invoke(){
        return budgetRemoteRepository.insertRemoteBudgetToLocal()
    }
}