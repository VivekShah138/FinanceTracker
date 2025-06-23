package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository

class InsertBudgetLocalUseCase(
    private val  budgetLocalRepository: BudgetLocalRepository
) {
    suspend operator fun invoke(budget:Budget){
        return budgetLocalRepository.insertBudget(budget = budget)
    }
}