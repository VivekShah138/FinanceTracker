package com.example.financetracker.domain.model


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

