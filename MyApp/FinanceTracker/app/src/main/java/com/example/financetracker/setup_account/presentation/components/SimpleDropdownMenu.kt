package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SimpleDropdownMenu(
    label: String,
    selectedText: String,
    expanded: Boolean,
    list: List<T>,
    onExpandedChange: (Boolean) -> Unit, // Change of expansion for dropdown
    onDismissRequest: () -> Unit, // Dismiss request to close expand
    displayText: (T) -> String, // Lambda to provide display text for each item
    onItemSelect: (T) -> Unit  // Lambda to handle item selection
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                label = { Text(label) },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor() // Needed for correct dropdown positioning
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { item ->
                    val displayValue = displayText(item)  // Get the display value

                    DropdownMenuItem(
                        onClick = {
                            onItemSelect(item)  // Handle item selection
                        },
                        text = { Text(text = displayValue) }
                    )
                }
            }
        }
    }
}
