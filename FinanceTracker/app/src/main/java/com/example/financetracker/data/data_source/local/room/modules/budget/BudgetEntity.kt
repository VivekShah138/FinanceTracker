package com.example.financetracker.data.data_source.local.room.modules.budget

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "monthly_budgets",
    indices = [Index(value = ["userId", "month", "year"], unique = true)]
)
data class BudgetEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val amount: Double,
    val month: Int, // 1 to 12
    val year: Int,  // e.g., 2025
    val updatedAt: Long,
    val cloudSync: Boolean,
    val receiveAlerts: Boolean,
    val thresholdAmount: Float
)
