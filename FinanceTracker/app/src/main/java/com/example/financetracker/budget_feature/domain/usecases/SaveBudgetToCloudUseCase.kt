package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository
import com.example.financetracker.budget_feature.domain.repository.BudgetRemoteRepository

class SaveBudgetToCloudUseCase(
    private val  budgetRemoteRepository: BudgetRemoteRepository
) {
    suspend operator fun invoke(userId: String,budget:Budget){
        return budgetRemoteRepository.uploadSingleBudgetToCloud(userId = userId,budget = budget)
    }
}