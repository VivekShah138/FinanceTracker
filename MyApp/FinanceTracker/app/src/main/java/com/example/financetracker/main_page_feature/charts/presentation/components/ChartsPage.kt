package com.example.financetracker.main_page_feature.charts.presentation.components

import BottomNavigationBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
//import android.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.components.AppTopBar


import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.main_page_feature.charts.presentation.ChartEvents
import com.example.financetracker.main_page_feature.charts.presentation.ChartsViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import javax.annotation.meta.When
import kotlin.random.Random

@Composable
fun ChartsPage(
    navController: NavController,
    viewModel: ChartsViewModel
){

    val states by viewModel.chartStates.collectAsStateWithLifecycle()

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
            bottomBar = {
                BottomNavigationBar(navController)
            }

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
                        selectedType = "Expense"
                    )
                }



                when(states.transactionType){
                    "Expense" -> {
                        ExpenseTabsScreen(
                            monthlyExpenses = states.expenseDataWithCategory,
                            yearlyExpenses = states.expenseDataWithCategory
                        )
                    }
                    "Income" -> {
                        ExpenseTabsScreen(
                            monthlyExpenses = states.incomeDataWithCategory,
                            yearlyExpenses = states.incomeDataWithCategory
                        )
                    }

                }


//                val expenses = mapOf(
//                    "Food" to 120.0,
//                    "Transport" to 80.0,
//                    "Rent" to 500.0,
//                    "Entertainment" to 60.0
//                )
//
//                Log.d("ChartsPage","expense: ${expenses}")
//
//                Log.d("ChartsPage","incomeDataWithCategory: ${states.incomeDataWithCategory}")
//                Log.d("ChartsPage","expenseDataWithCategory: ${states.expenseDataWithCategory}")
//
//                ExpensePieChartWithLegend(states.expenseDataWithCategory)

            }
        }
    }
}
