package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.domain.model.Budget
import com.example.financetracker.domain.repository.local.BudgetLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllUnSyncedBudgetLocalUseCase(
    private val  budgetLocalRepository: BudgetLocalRepository
) {

    suspend operator fun invoke(userId: String): Flow<List<Budget>> {
        return budgetLocalRepository.getAllUnSyncedBudget(userId = userId)
    }

}