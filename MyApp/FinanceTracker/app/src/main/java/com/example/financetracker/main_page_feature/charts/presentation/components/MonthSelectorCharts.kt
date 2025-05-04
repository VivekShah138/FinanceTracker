package com.example.financetracker.main_page_feature.charts.presentation.components

import android.app.DatePickerDialog
import android.content.Context
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


