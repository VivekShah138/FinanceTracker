package com.example.financetracker.presentation.features.setup_account_feature.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun Names(
    firstName: String,
    lastName: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
    ) {
        // Country Code Input
        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text("First Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),

            modifier = Modifier.weight(1f) // Adjust width as needed
        )

        Spacer(modifier = Modifier.width(8.dp)) // Adds space between fields

        // Phone Number Input
        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text("Last Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.weight(1f) // Takes remaining space
        )
    }
}