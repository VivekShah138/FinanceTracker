package com.example.financetracker.presentation.features.charts_feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
//import android.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.presentation.features.charts_feature.ChartEvents
import com.example.financetracker.presentation.features.charts_feature.ChartStates
import com.example.financetracker.presentation.features.charts_feature.ChartsViewModel
import com.example.financetracker.ui.theme.FinanceTrackerTheme


@Composable
fun ChartsRoot(
    viewModel: ChartsViewModel = hiltViewModel()
){
    val states by viewModel.chartStates.collectAsStateWithLifecycle()

    ChartsScreen(
        states = states,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ChartsScreen(
    states: ChartStates,
    onEvent: (ChartEvents) -> Unit
){
    val context = LocalContext.current

    MaterialTheme {
        Scaffold { padding ->

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    DurationDropDown(
                        onDurationSelected = {
                            onEvent(ChartEvents.ChangeType(type = it, state = false))
                        },
                        onRangeDropDownClick = {
                            onEvent(ChartEvents.ChangeType(type = states.transactionType, state = true))
                        },
                        onRangeDropDownDismiss = {
                            onEvent(ChartEvents.ChangeType(type = states.transactionType, state = false))
                        },
                        rangeDropDownExpanded = states.typeDropDown,
                        filterOptions = listOf("Expense","Income"),
                        selectedType = states.transactionType
                    )
                }

                when(states.transactionType){
                    "Expense" -> {
                        ChartTabsScreen(
                            monthlyExpenses = states.expenseDataWithCategory,
                            yearlyExpenses = states.expenseDataWithCategory,
                            showOnlyYear = states.showOnlyYear,
                            states = states,
                            context = context,
                            onEvent = onEvent
                        )
                    }
                    "Income" -> {
                        ChartTabsScreen(
                            monthlyExpenses = states.incomeDataWithCategory,
                            yearlyExpenses = states.incomeDataWithCategory,
                            showOnlyYear = states.showOnlyYear,
                            states = states,
                            context = context,
                            onEvent = onEvent
                        )
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun ChartsPagePreview() {
    FinanceTrackerTheme {
        ChartsScreen(
            states = ChartStates(),
            onEvent = {

            }
        )
    }
}