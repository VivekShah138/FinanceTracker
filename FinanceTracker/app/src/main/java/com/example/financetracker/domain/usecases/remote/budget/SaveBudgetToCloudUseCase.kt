package com.example.financetracker.domain.usecases.remote.budget

import com.example.financetracker.domain.model.Budget
import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository

class SaveBudgetToCloudUseCase(
    private val  budgetRemoteRepository: BudgetRemoteRepository
) {
    suspend operator fun invoke(userId: String,budget: Budget){
        return budgetRemoteRepository.uploadSingleBudgetToCloud(userId = userId,budget = budget)
    }
}