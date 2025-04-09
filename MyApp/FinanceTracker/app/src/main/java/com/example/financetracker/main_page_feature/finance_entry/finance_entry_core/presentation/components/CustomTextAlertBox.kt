package com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextAlertBox(
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onSaveCategory: () -> Unit,
    title: String,
    label: String
){
    AlertDialog(
        onDismissRequest =  onDismissRequest ,
        title = { Text(text = title) },
        text = {
            Column {
                TextField(
                    value = selectedCategory,
                    onValueChange =  onCategoryChange,
                    label = { Text(label) }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedCategory.isNotBlank()) {
                        onSaveCategory()
                    }
                }
            ) {
                Text("Save")
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