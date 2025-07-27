package com.example.financetracker.presentation.features.setup_account_feature.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmailDisplay(label: String, email: String, onChangeEmailClick: () -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = {}, // No direct editing, only display
        label = { Text(label) },
        readOnly = true, // Prevents direct typing
//        trailingIcon = {
//            IconButton(onClick = onChangeEmailClick) {
//                Icon(imageVector = Icons.Default.Edit, contentDescription = "Change Email")
//            }
//        },
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
    )
}