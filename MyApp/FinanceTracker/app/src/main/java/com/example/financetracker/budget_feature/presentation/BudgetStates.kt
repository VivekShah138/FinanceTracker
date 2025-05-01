package com.example.financetracker.budget_feature.presentation

import java.util.Calendar

data class BudgetStates(
    val budget: String = "",
    val budgetCurrencySymbol: String = "",
    val budgetEditState: Boolean = false,
    val nextMonthVisibility: Boolean = false,
    val selectedYear: Int = 0,
    val selectedMonth: Int = 0
)
