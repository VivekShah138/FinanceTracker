package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsStates

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangeBottomSheet(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    onDateRangeSelected: (Long, Long) -> Unit
) {
    var fromDate by remember { mutableStateOf<Calendar?>(null) }
    var toDate by remember { mutableStateOf<Calendar?>(null) }
    var dateError by remember { mutableStateOf<String?>(null) }



    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Select Custom Date Range", style = MaterialTheme.typography.titleMedium)

            DatePickerField(
                date = fromDate,
                onDateSelected = { fromDate = it },
                label = "From"
            )

            DatePickerField(
                date = toDate,
                onDateSelected = {
                    toDate = it

                    // Validate as soon as user picks the "To" date
                    if (fromDate != null && it.timeInMillis < fromDate!!.timeInMillis) {
                        dateError = "From date must be before To date"
                    } else {
                        dateError = null
                    }
                },
                label = "To"
            )

            if (dateError != null) {
                Text(
                    text = dateError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    onDateRangeSelected(
                        fromDate!!.timeInMillis,
                        toDate!!.timeInMillis
                    )
                    onDismiss()
                },
                enabled = fromDate != null && toDate != null && dateError == null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Apply")
            }



            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}




@Composable
fun DatePickerField(
    date: Calendar?,
    onDateSelected: (Calendar) -> Unit,
    label:String
) {
    val context = LocalContext.current
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value =  date?.let { formatter.format(it.time) } ?: "Select Date",
        onValueChange = {}, // no manual editing
        readOnly = true,
        label = { Text(label) },
        interactionSource = interactionSource,
        modifier = Modifier.fillMaxWidth()
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                val today = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }
                        onDateSelected(selectedDate)
                    },
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    datePicker.maxDate = Calendar.getInstance().timeInMillis
                }.show()
            }
        }
    }
}

