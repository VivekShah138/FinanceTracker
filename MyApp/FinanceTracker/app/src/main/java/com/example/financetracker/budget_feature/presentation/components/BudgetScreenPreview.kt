package com.example.financetracker.budget_feature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BudgetScreenPreview(

){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        OutlinedTextField(
            value = "100",
            onValueChange = {}, // No direct editing, only display
            label = { Text("budget") },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Change Budget")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}