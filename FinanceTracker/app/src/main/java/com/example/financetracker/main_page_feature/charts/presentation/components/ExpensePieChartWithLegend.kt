package com.example.financetracker.main_page_feature.charts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.financetracker.budget_feature.presentation.BudgetStates
import com.example.financetracker.budget_feature.presentation.components.MonthSelectorBudget
import com.example.financetracker.main_page_feature.charts.presentation.ChartStates
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlin.random.Random

@Composable
fun ExpensePieChartWithLegend(expenseData: Map<String, Double>,currencySymbol: String = "$") {
    val centerTextColor = MaterialTheme.colorScheme.onBackground.toArgb()
    val holeColor = MaterialTheme.colorScheme.background.toArgb()

    val entries = expenseData.map { (category, amount) ->
        PieEntry(amount.toFloat(), category)
    }


    val colors = remember(expenseData.keys.size) {
        generateColors(expenseData.keys.size)
    }


    val totalAmount = expenseData.values.sum() // Calculate the total sum of all expenses

    val legendEntries = entries.mapIndexed { index, pieEntry ->
        val category = pieEntry.label
        val amount = expenseData[category] ?: 0.0
        val percentage = if (totalAmount > 0) (amount / totalAmount) * 100 else 0.0

        LegendEntryData(
            label = "$category:   ${"%.2f".format(percentage)}%", // Show percentage instead of amount
            colors[entries.indexOf(pieEntry)].toArgb()
        )
    }


//    MonthSelectorBudget(
//        state = BudgetStates(),
//        onEvent = {},
//        context = LocalContext.current
//    )

//    MonthSelectorCharts(
//        state = ChartStates(),
//        onEvent = {
//
//        },
//        context = LocalContext.current
//    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pie Chart (top)
        AndroidView(
            modifier = Modifier.size(300.dp),
            factory = { context ->
                PieChart(context).apply {
                    description.isEnabled = false
                    legend.isEnabled = false
                    setDrawEntryLabels(false)
                }
            },
            update = { pieChart ->
                val entries = expenseData.map { (category, amount) ->
                    PieEntry(amount.toFloat(), category)
                }
                val colors = colors

                val dataSet = PieDataSet(entries, "").apply {
                    this.colors = colors.map { it.toArgb() }
                    setDrawValues(false)
                }
                val data = PieData(dataSet)

                pieChart.data = data
                pieChart.centerText = "Total\n${"$currencySymbol%.2f".format(expenseData.values.sum())}"
                pieChart.setCenterTextColor(centerTextColor)
                pieChart.setHoleColor(holeColor)
                pieChart.invalidate()  // Force redraw
            }
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Legend below chart (in 2 vertical columns)
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            // First column for legend items
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                legendEntries.filterIndexed { index, _ -> index % 2 == 0 }.forEach { entry ->
                    LegendItem(entry.label, entry.color)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Second column for legend items
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                legendEntries.filterIndexed { index, _ -> index % 2 == 1 }.forEach { entry ->
                    LegendItem(entry.label, entry.color)
                }
            }
        }
    }
}


fun generateColors(count: Int): List<Color> {
    val colors = mutableListOf<Color>()

    for (i in 0 until count) {
        // Generate random values for Red, Green, and Blue between 0 and 255
        val r = Random.nextInt(0, 256)
        val g = Random.nextInt(0, 256)
        val b = Random.nextInt(0, 256)

        // Create a color with the random RGB values
        val color = Color(r, g, b)

        colors.add(color)
    }
    return colors
}

@Composable
fun LegendItem(label: String, color: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(Color(color), shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

data class LegendEntryData(val label: String, val color: Int)