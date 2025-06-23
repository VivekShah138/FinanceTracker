package com.example.financetracker.main_page_feature.charts.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.financetracker.main_page_feature.charts.presentation.ChartEvents
import com.example.financetracker.main_page_feature.charts.presentation.ChartStates


@Composable
fun ChartTabsScreen(
    monthlyExpenses: Map<String, Double>,
    yearlyExpenses: Map<String, Double>,
    states: ChartStates,
    context: Context,
    showOnlyYear: Boolean,
    onEvent: (ChartEvents) -> Unit
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
                MonthTabScreen(
                    states = states,
                    context = context,
                    monthlyExpenses = monthlyExpenses,
                    showOnlyYear = showOnlyYear,
                    onEvent = onEvent
                )
                onEvent(
                    ChartEvents.ChangeDateToYear(state = false)
                )
                // Or replace with your states.expenseDataWithCategory if needed
            }
            1 -> {
                // Yearly Expenses Screen
                YearTabScreen(
                    states = states,
                    context = context,
                    yearlyExpenses = yearlyExpenses,
                    showOnlyYear = showOnlyYear,
                    onEvent = onEvent
                )
                onEvent(
                    ChartEvents.ChangeDateToYear(state = true)
                )
            }
        }
    }
}
