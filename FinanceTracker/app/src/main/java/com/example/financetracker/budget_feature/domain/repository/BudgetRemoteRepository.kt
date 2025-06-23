package com.example.financetracker.budget_feature.domain.repository

import com.example.financetracker.budget_feature.domain.model.Budget

interface BudgetRemoteRepository {

    suspend fun uploadSingleBudgetToCloud(userId: String, budget: Budget)
    suspend fun uploadMultipleBudgetsToCloud()
    suspend fun getRemoteBudget(userId:String): List<Budget>
    suspend fun insertRemoteBudgetToLocal()

}