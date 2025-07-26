package com.example.financetracker.budget_feature.domain.usecases

import com.example.financetracker.domain.model.Budget
import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository

class GetRemoteBudgetsList(
    private val budgetRemoteRepository: BudgetRemoteRepository,
) {

    suspend operator fun invoke(userId: String): List<Budget>{
        return budgetRemoteRepository.getRemoteBudget(userId)
    }
}