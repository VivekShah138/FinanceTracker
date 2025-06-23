package com.example.financetracker.main_page_feature.charts.presentation

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions

data class ChartStates(
    val incomeDataWithCategory: Map<String, Double> = emptyMap(),
    val expenseDataWithCategory: Map<String, Double> = emptyMap(),
    val transactionsList: List<Transactions> = emptyList(),
    val selectedYear: Int = 0,
    val selectedOnlyYear: Int = 0,
    val selectedMonth: Int = 0,
    val nextMonthVisibility: Boolean = false,
    val transactionType: String = "Expense",
    val typeDropDown: Boolean = false,
    val showOnlyYear: Boolean = false,
    val baseCurrencySymbol: String = ""
)
