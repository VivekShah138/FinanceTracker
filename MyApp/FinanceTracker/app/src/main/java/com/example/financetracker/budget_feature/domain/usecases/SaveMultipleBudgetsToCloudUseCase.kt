package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository
import com.example.financetracker.budget_feature.domain.repository.BudgetRemoteRepository

class SaveMultipleBudgetsToCloudUseCase(
    private val  budgetRemoteRepository: BudgetRemoteRepository
) {
    suspend operator fun invoke(){
        return budgetRemoteRepository.uploadMultipleBudgetsToCloud()
    }
}