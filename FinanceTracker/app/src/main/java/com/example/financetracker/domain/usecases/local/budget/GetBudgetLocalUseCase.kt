package com.example.financetracker.domain.usecases.local.budget

import com.example.financetracker.domain.model.Budget
import com.example.financetracker.domain.repository.local.BudgetLocalRepository

class GetBudgetLocalUseCase(
    private val  budgetLocalRepository: BudgetLocalRepository
) {

    suspend operator fun invoke(userId: String,month: Int,year: Int): Budget?{
        return budgetLocalRepository.getBudgetForMonth(userId = userId,month = month,year = year)
    }

}