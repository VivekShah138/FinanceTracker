package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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