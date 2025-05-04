package com.example.financetracker.main_page_feature.charts.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun ExpenseTabsScreen(
    monthlyExpenses: Map<String, Double>,
    yearlyExpenses: Map<String, Double>
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Month", "Year")

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> {
                // Monthly Expenses Screen
                ExpensePieChartWithLegend(monthlyExpenses)
                // Or replace with your states.expenseDataWithCategory if needed
            }
            1 -> {
                // Yearly Expenses Screen
                ExpensePieChartWithLegend(yearlyExpenses)
            }
        }
    }
}
