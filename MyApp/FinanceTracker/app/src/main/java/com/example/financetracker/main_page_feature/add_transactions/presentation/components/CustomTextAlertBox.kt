package com.example.financetracker.main_page_feature.add_transactions.presentation.components

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
    onSaveCategory: () -> Unit
){
    AlertDialog(
        onDismissRequest =  onDismissRequest ,
        title = { Text(text = "Enter Title") },
        text = {
            Column {
                TextField(
                    value = selectedCategory,
                    onValueChange =  onCategoryChange,
                    label = { Text("Title") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedCategory.isNotBlank()) {
                        onSaveCategory()
                        onDismissRequest()
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