package com.example.financetracker.budget_feature.presentation.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.financetracker.budget_feature.presentation.BudgetEvents
import com.example.financetracker.budget_feature.presentation.BudgetStates
import java.text.SimpleDateFormat
import java.util.*



@Composable
fun MonthSelectorBudget(
    state: BudgetStates,
    onEvent: (BudgetEvents) -> Unit,
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
            onClick = { onEvent(BudgetEvents.PreviousMonthClicked) },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Previous Month",
                tint = MaterialTheme.colorScheme.onBackground
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
                            onEvent(BudgetEvents.MonthSelected(year, month))
                        },
                        state.selectedYear,
                        state.selectedMonth,
                        1 // day unused
                    ).apply {
                        datePicker.maxDate = Calendar.getInstance().timeInMillis
                    }.show()
                },
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleMedium
        )

        if(state.nextMonthVisibility){
            IconButton(
                onClick = { onEvent(BudgetEvents.NextMonthClicked) },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Next Month",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


