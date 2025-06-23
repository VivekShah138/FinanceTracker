package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ChangePasswordSection(onChangePasswordClick: () -> Unit) {
    OutlinedTextField(
        value = "",  // No text since password can't be displayed
        onValueChange = {},
        label = { Text("Change Password") },
        readOnly = true,  // Prevent direct typing
        trailingIcon = {
            IconButton(onClick = onChangePasswordClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Change Password"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
    )
}