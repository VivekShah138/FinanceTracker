package com.example.financetracker.presentation.features.budget_feature

data class BudgetStates(
    val budget: String = "",
    val budgetCurrencySymbol: String = "",
    val createBudgetState: Boolean = false,
    val nextMonthVisibility: Boolean = false,
    val selectedYear: Int = 0,
    val selectedMonth: Int = 0,
    val receiveAlerts: Boolean = false,
    val alertThresholdPercent: Float = 0f
)
