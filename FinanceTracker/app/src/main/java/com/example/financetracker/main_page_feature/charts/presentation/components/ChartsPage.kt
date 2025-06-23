package com.example.financetracker.main_page_feature.charts.presentation.components

import BottomNavigationBar
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
//import android.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.main_page_feature.charts.presentation.ChartEvents
import com.example.financetracker.main_page_feature.charts.presentation.ChartsViewModel

@Composable
fun ChartsPage(
    navController: NavController,
    viewModel: ChartsViewModel
){

    val states by viewModel.chartStates.collectAsStateWithLifecycle()
    val context = LocalContext.current

    MaterialTheme {
        Scaffold(
//            topBar = {
//                AppTopBar(
//                    title = "Charts",
//                    showMenu = true,
//                    showBackButton = false,
//                    onBackClick = {},
//                    menuItems = emptyList()
//                )
//            },
//            bottomBar = {
//                BottomNavigationBar(navController)
//            }

        ) { padding ->

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
                            viewModel.onEvent(ChartEvents.ChangeType(type = it, state = false))
                        },
                        onRangeDropDownClick = {
                            viewModel.onEvent(ChartEvents.ChangeType(type = states.transactionType, state = true))
                        },
                        onRangeDropDownDismiss = {
                            viewModel.onEvent(ChartEvents.ChangeType(type = states.transactionType, state = false))
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
                            onEvent = viewModel::onEvent
                        )
                    }
                    "Income" -> {
                        ChartTabsScreen(
                            monthlyExpenses = states.incomeDataWithCategory,
                            yearlyExpenses = states.incomeDataWithCategory,
                            showOnlyYear = states.showOnlyYear,
                            states = states,
                            context = context,
                            onEvent = viewModel::onEvent
                        )
                    }

                }
            }
        }
    }
}
