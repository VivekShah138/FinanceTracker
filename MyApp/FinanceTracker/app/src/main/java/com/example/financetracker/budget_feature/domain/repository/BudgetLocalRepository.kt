package com.example.financetracker.budget_feature.domain.repository

import com.example.financetracker.budget_feature.domain.model.Budget

interface BudgetLocalRepository {

    suspend fun getBudgetForMonth(userId: String, month: Int, year: Int): Budget?
    suspend fun insertBudget(budget: Budget)

}