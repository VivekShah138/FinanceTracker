package com.example.financetracker.presentation.features.budget_feature

sealed class BudgetEvents {

    data class ChangeBudget(val budget: String): BudgetEvents()
    data class ChangeCreateBudgetState(val state: Boolean): BudgetEvents()
    data class ChangeReceiveBudgetAlerts(val state: Boolean): BudgetEvents()
    data class ChangeAlertThresholdAmount(val amount: Float): BudgetEvents()
    data object SaveBudget: BudgetEvents()
    object PreviousMonthClicked : BudgetEvents()
    object NextMonthClicked : BudgetEvents()
    data class MonthSelected(val year: Int, val month: Int) : BudgetEvents()

}