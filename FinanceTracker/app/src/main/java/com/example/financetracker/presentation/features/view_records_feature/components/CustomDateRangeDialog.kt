package com.example.financetracker.presentation.features.view_records_feature.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import android.app.DatePickerDialog
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import java.text.SimpleDateFormat
import java.util.*


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

