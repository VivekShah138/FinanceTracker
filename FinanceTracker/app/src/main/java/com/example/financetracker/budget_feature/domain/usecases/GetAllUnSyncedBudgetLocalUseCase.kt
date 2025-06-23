package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllUnSyncedBudgetLocalUseCase(
    private val  budgetLocalRepository: BudgetLocalRepository
) {

    suspend operator fun invoke(userId: String): Flow<List<Budget>> {
        return budgetLocalRepository.getAllUnSyncedBudget(userId = userId)
    }

}