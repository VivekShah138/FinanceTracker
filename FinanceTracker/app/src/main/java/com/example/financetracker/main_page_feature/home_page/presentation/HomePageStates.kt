package com.example.financetracker.main_page_feature.home_page.presentation

data class HomePageStates(
    val incomeAmount: String = "0",
    val expenseAmount: String = "0",
    val currencySymbol: String = "",
    val accountBalance: String = "",
    val monthlyBudget: Double = 0.0,
    val receiveAlert: Float = 0f,
    val incomeDataWithCategory: Map<String, Double> = emptyMap(),
    val expenseDataWithCategory: Map<String, Double> = emptyMap(),
    val budgetExist: Boolean = false,
)
