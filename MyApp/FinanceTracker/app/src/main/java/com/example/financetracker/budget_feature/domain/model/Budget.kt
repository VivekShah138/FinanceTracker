package com.example.financetracker.budget_feature.domain.model

import com.example.financetracker.budget_feature.data.data_source.BudgetEntity
import java.util.UUID

data class Budget(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val amount: Double,
    val month: Int, // 1 to 12
    val year: Int,  // e.g., 2025
    val updatedAt: Long = System.currentTimeMillis(),
    val cloudSync: Boolean,
    val receiveAlerts: Boolean,
    val thresholdAmount: Float
)

fun Budget.toEntity(): BudgetEntity {
    return BudgetEntity(
        id = id,
        userId = userId,
        amount = amount,
        month = month,
        year = year,
        updatedAt = updatedAt,
        cloudSync = cloudSync,
        receiveAlerts = receiveAlerts,
        thresholdAmount = thresholdAmount
    )
}


fun BudgetEntity.toDomain(): Budget {
    return Budget(
        id = id,
        userId = userId,
        amount = amount,
        month = month,
        year = year,
        updatedAt = updatedAt,
        cloudSync = cloudSync,
        receiveAlerts = receiveAlerts,
        thresholdAmount = thresholdAmount
    )
}
