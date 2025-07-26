package com.example.financetracker.data.local.data_source.room.modules.budget

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Query("SELECT * FROM monthly_budgets WHERE userId = :userId AND month = :month AND year = :year LIMIT 1")
    suspend fun getBudgetForMonth(userId: String, month: Int, year: Int): BudgetEntity?

    @Query("SELECT * FROM monthly_budgets WHERE userId = :userId AND cloudSync == 0")
    fun getAllUnSyncedBudget(userId: String): Flow<List<BudgetEntity>>

    @Upsert
    suspend fun insertBudget(budget: BudgetEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM MONTHLY_BUDGETS WHERE id = :id AND userId = :userId LIMIT 1)")
    suspend fun doesBudgetExist(userId: String, id: String): Boolean
}
