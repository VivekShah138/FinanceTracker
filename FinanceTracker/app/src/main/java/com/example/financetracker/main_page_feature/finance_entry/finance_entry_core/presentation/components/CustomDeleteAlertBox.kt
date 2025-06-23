package com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDeleteAlertBox(
    displayDeleteMessage: String,
    selectedCategory: String,
    onDismissRequest: () -> Unit,
    onDeleteItem: () -> Unit
){
    AlertDialog(
        onDismissRequest =  onDismissRequest ,
        title = { Text(text = "Delete Item?") },
        text = {
            Column {
                Text(text = displayDeleteMessage)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedCategory.isNotBlank()) {
                        onDeleteItem()
                        onDismissRequest()
                    }
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}