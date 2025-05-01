package com.example.financetracker.budget_feature.data.repository

import com.example.financetracker.budget_feature.data.data_source.BudgetDao
import com.example.financetracker.budget_feature.data.data_source.BudgetEntity
import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.model.toDomain
import com.example.financetracker.budget_feature.domain.model.toEntity
import com.example.financetracker.budget_feature.domain.repository.BudgetLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetLocalRepositoryImpl(
    private val budgetDao: BudgetDao
): BudgetLocalRepository {
    override suspend fun getBudgetForMonth(userId: String, month: Int, year: Int): Budget? {
        return budgetDao.getBudgetForMonth(userId = userId,month = month,year = year)?.toDomain()
    }

    override suspend fun getAllUnSyncedBudget(userId: String): Flow<List<Budget>> {
        return budgetDao.getAllUnSyncedBudget(userId).map { budgets ->
            budgets.map { budget ->
                budget.toDomain()
            }
        }
    }

    override suspend fun insertBudget(budget: Budget) {
        return budgetDao.insertBudget(budget = budget.toEntity())
    }
}