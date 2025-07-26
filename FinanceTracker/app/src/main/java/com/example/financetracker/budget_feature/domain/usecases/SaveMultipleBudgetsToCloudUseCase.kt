package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository

class SaveMultipleBudgetsToCloudUseCase(
    private val  budgetRemoteRepository: BudgetRemoteRepository
) {
    suspend operator fun invoke(){
        return budgetRemoteRepository.uploadMultipleBudgetsToCloud()
    }
}