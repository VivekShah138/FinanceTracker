package com.example.financetracker.domain.repository.remote

import com.example.financetracker.domain.model.Budget

interface BudgetRemoteRepository {

    suspend fun uploadSingleBudgetToCloud(userId: String, budget: Budget)
    suspend fun uploadMultipleBudgetsToCloud()
    suspend fun getRemoteBudget(userId:String): List<Budget>
    suspend fun insertRemoteBudgetToLocal()

}