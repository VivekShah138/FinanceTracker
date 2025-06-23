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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun ChartsPagePreview(){
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
//                BottomNavigationBar()
//            }

        ) { padding ->

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                val expenses = mapOf(
                    "Food" to 120.0,
                    "Transport" to 80.0,
                    "Rent" to 500.0,
                    "Entertainment" to 600.0,
                    "Entertainment2" to 6.0,
                    "Entertainment3" to 50.0,
                    "Entertainment4" to 70.0,
                    "Entertainment5" to 800.0,
                    "Entertainment6" to 90.0,
                    "Entertainment7" to 160.0,
                    "Entertainment8" to 360.0,
                )
//
////                ExpensePieChart(expenses)
//                ExpensePieChartWithLegend(expenses)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    DurationDropDown(
                        onDurationSelected = {

                        },
                        onRangeDropDownClick = {

                        },
                        onRangeDropDownDismiss = {

                        },
                        rangeDropDownExpanded = false,
                        filterOptions = listOf("Expense","Income"),
                        selectedType = "Expense"
                    )
                }

//                ChartTabsScreen(
//                    monthlyExpenses = expenses,
//                    yearlyExpenses = expenses
//                )



            }
        }
    }
}