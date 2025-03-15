package com.example.financetracker.setup_account.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.setup_account.presentation.SetUpAccountPageViewModel

@Composable
fun SetUpAccountPage(
    viewModel: SetUpAccountPageViewModel,
    navController: NavController
){

    val states by viewModel.setUpAccountPageStates.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.setUserEmail()
    }

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