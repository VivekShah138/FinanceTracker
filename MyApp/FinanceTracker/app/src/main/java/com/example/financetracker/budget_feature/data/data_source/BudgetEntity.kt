package com.example.financetracker.budget_feature.data.data_source

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

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
