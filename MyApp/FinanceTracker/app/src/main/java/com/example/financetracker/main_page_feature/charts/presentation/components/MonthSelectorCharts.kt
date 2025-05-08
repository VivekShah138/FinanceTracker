package com.example.financetracker.main_page_feature.charts.presentation.components

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.NumberPicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.financetracker.main_page_feature.charts.presentation.ChartEvents
import com.example.financetracker.main_page_feature.charts.presentation.ChartStates
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun MonthSelectorCharts(
    state: ChartStates,
    onEvent: (ChartEvents) -> Unit,
    context: Context
){

    val formatter = remember { SimpleDateFormat("MMMM yyyy", Locale.getDefault()) }

    val cal = Calendar.getInstance().apply {
        set(Calendar.YEAR, state.selectedYear)
        set(Calendar.MONTH, state.selectedMonth)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { onEvent(ChartEvents.PreviousMonthClicked) },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Previous Month",
//                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            text = formatter.format(cal.time),
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {

                    DatePickerDialog(
                        context,
                        { _, year, month, _ ->
                            onEvent(ChartEvents.MonthSelected(year, month))
                        },
                        state.selectedYear,
                        state.selectedMonth,
                        1 // day unused
                    ).apply {
                        datePicker.maxDate = Calendar.getInstance().timeInMillis
                    }.show()
                },
//            color = MaterialTheme.colorScheme.onPrimary
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleMedium
        )

        if(state.nextMonthVisibility){
            IconButton(
                onClick = { onEvent(ChartEvents.NextMonthClicked) },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Next Month",
//                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun MonthSelectorCharts2(
    state: ChartStates,
    onEvent: (ChartEvents) -> Unit,
    context: Context,
    showOnlyYear: Boolean // ✅ added this flag
) {
    val formatter = remember { SimpleDateFormat("MMMM yyyy", Locale.getDefault()) }
    val yearFormatter = remember { SimpleDateFormat("yyyy", Locale.getDefault()) }

    val cal =  Calendar.getInstance().apply {
        if(showOnlyYear) set(Calendar.YEAR, state.selectedOnlyYear) else
        set(Calendar.YEAR, state.selectedYear)
        set(Calendar.MONTH, state.selectedMonth)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                if (showOnlyYear) onEvent(ChartEvents.PreviousYearClicked)
                else onEvent(ChartEvents.PreviousMonthClicked)
            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = if (showOnlyYear) "Previous Year" else "Previous Month"
            )
        }

        Text(
            text = if (showOnlyYear) yearFormatter.format(cal.time) else formatter.format(cal.time),
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    if (showOnlyYear) {
                        // ✅ Year picker dialog
                        showYearPickerDialog(context, state.selectedOnlyYear) { selectedYear ->
                            onEvent(ChartEvents.YearSelected(selectedYear))
                        }
                    } else {
                        // ✅ Month picker dialog
                        DatePickerDialog(
                            context,
                            { _, year, month, _ ->
                                onEvent(ChartEvents.MonthSelected(year, month))
                            },
                            state.selectedYear,
                            state.selectedMonth,
                            1
                        ).apply {
                            datePicker.maxDate = Calendar.getInstance().timeInMillis
                        }.show()
                    }
                },
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleMedium
        )

        if (state.nextMonthVisibility) {
            IconButton(
                onClick = {
                    if (showOnlyYear) onEvent(ChartEvents.NextYearClicked)
                    else onEvent(ChartEvents.NextMonthClicked)
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = if (showOnlyYear) "Next Year" else "Next Month"
                )
            }
        }
    }
}


fun showYearPickerDialog(
    context: Context,
    initialYear: Int,
    onYearSelected: (Int) -> Unit
) {
    val picker = NumberPicker(context).apply {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        minValue = 1900
        maxValue = currentYear
        value = initialYear
    }

    AlertDialog.Builder(context)
        .setTitle("Select Year")
        .setView(picker)
        .setPositiveButton("OK") { _, _ ->
            onYearSelected(picker.value)
        }
        .setNegativeButton("Cancel", null)
        .show()
}



