package com.example.financetracker.budget_feature.presentation

data class BudgetStates(
    val budget: String = "",
    val budgetCurrencySymbol: String = "",
    val budgetEditState: Boolean = false
)
