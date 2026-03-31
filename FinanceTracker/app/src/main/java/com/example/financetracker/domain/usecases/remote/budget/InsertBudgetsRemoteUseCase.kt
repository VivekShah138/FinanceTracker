package com.example.financetracker.domain.usecases.remote.budget

import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository

class InsertBudgetsRemoteUseCase(
    private val  budgetRemoteRepository: BudgetRemoteRepository
) {
    suspend operator fun invoke(){
        return budgetRemoteRepository.uploadMultipleBudgetsToCloud()
    }
}