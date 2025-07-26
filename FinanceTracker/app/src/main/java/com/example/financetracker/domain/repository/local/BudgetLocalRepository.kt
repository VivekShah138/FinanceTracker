package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetLocalRepository {

    suspend fun getBudgetForMonth(userId: String, month: Int, year: Int): Budget?
    suspend fun getAllUnSyncedBudget(userId: String): Flow<List<Budget>>
    suspend fun insertBudget(budget: Budget)
    suspend fun sendBudgetNotifications(title: String, message: String)
    suspend fun doesBudgetExist(userId: String, id: String): Boolean

}