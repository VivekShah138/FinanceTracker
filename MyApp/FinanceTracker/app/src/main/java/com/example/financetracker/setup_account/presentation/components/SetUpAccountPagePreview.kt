package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.setup_account.presentation.SetUpAccountPageStates


@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun SetUpAccountPreview() {
    MaterialTheme { // Wrap with MaterialTheme to apply colors properly

        Content(
            SetUpAccountPageStates(
                email = "shahvivek138@gmail.com"
            )
        )
    }
}


@Composable
fun Content(
    states: SetUpAccountPageStates
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("EMail")
        Text("${states.email}")
        Spacer(modifier = Modifier.height(20.dp))
        Text("Password")
        Text("Password")
        Spacer(modifier = Modifier.height(20.dp))
        Text("Default Currency")
        Text("Default Currency")
        Spacer(modifier = Modifier.height(20.dp))
        Text("Budget SetUp")
        Text("Budget Setup")
        Spacer(modifier = Modifier.height(20.dp))
        Text("Income Categories")
        Spacer(modifier = Modifier.height(20.dp))
        Text("Expense Categories")
        Spacer(modifier = Modifier.height(20.dp))
        Text("Notification/Reminders")
        Spacer(modifier = Modifier.height(20.dp))
        Text("Cloud Sync")
    }

}