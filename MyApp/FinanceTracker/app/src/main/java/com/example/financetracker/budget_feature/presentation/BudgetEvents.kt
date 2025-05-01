package com.example.financetracker.budget_feature.presentation

sealed class BudgetEvents {

    data class ChangeBudget(val budget: String): BudgetEvents()
    data class ChangeBudgetEditState(val state: Boolean): BudgetEvents()
    data object SaveBudget: BudgetEvents()

}