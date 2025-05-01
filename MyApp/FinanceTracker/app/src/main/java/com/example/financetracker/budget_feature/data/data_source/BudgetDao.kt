package com.example.financetracker.budget_feature.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BudgetDao {

    @Query("SELECT * FROM monthly_budgets WHERE userId = :userId AND month = :month AND year = :year LIMIT 1")
    suspend fun getBudgetForMonth(userId: String, month: Int, year: Int): BudgetEntity?

    @Upsert
    suspend fun insertBudget(budget: BudgetEntity)
}
