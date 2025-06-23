package com.example.financetracker.main_page_feature.charts.presentation.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financetracker.main_page_feature.charts.presentation.ChartEvents
import com.example.financetracker.main_page_feature.charts.presentation.ChartStates

@Composable
fun YearTabScreen(
    states: ChartStates,
    context: Context,
    yearlyExpenses: Map<String, Double>,
    showOnlyYear: Boolean,
    onEvent: (ChartEvents) -> Unit
){

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Log.d("YearTabScreeen" ,"show Only Year: $showOnlyYear")
        MonthSelectorCharts2(
            state = states,
            onEvent = onEvent,
            context = context,
            showOnlyYear = showOnlyYear
        )

        Spacer(modifier = Modifier.height(8.dp))

        if(yearlyExpenses.isEmpty()){
            Text("No Transactions")
        }
        else{
            ExpensePieChartWithLegend(expenseData = yearlyExpenses, currencySymbol = states.baseCurrencySymbol)
        }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp), // 👈 same height you'd use for the pie chart
//            contentAlignment = Alignment.Center
//        ) {
//            if (yearlyExpenses.isEmpty()) {
//                Text("No Transactions")
//            } else {
//                ExpensePieChartWithLegend(yearlyExpenses)
//            }
//        }


    }

}