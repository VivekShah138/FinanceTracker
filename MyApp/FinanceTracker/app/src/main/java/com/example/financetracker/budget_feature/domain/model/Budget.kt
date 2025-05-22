package com.example.financetracker.budget_feature.domain.model

import com.example.financetracker.budget_feature.data.data_source.BudgetEntity
import java.util.UUID

data class Budget(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val amount: Double = 0.0,
    val month: Int = 0,
    val year: Int = 0,
    val updatedAt: Long = System.currentTimeMillis(),
    val cloudSync: Boolean = false,
    val receiveAlerts: Boolean = false,
    val thresholdAmount: Float = 0F
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
