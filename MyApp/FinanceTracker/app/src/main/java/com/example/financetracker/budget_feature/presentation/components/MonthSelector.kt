package com.example.financetracker.budget_feature.presentation.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financetracker.budget_feature.presentation.BudgetEvents
import com.example.financetracker.budget_feature.presentation.BudgetStates
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MonthSelector(
    state: BudgetStates,
    onEvent: (BudgetEvents) -> Unit,
    context: Context
) {

    val formatter = remember { SimpleDateFormat("MMMM yyyy", Locale.getDefault()) }

    val cal = Calendar.getInstance().apply {
        set(Calendar.YEAR, state.selectedYear)
        set(Calendar.MONTH, state.selectedMonth)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { onEvent(BudgetEvents.PreviousMonthClicked) },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }

        Text(
            text = formatter.format(cal.time),
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {

                    DatePickerDialog(
                        context,
                        { _, year, month, _ ->
                            onEvent(BudgetEvents.MonthSelected(year, month))
                        },
                        state.selectedYear,
                        state.selectedMonth,
                        1 // day unused
                    ).apply {
                        datePicker.maxDate = Calendar.getInstance().timeInMillis
                    }.show()
                }
        )

        if(state.nextMonthVisibility){
            IconButton(
                onClick = { onEvent(BudgetEvents.NextMonthClicked) },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }
    }
}


