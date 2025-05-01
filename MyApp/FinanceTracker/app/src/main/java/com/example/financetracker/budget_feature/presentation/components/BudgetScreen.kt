package com.example.financetracker.budget_feature.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.budget_feature.presentation.BudgetEvents
import com.example.financetracker.budget_feature.presentation.BudgetViewModel

import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel


@Composable
fun BudgetScreen(
    navController: NavController,
    budgetViewModel: BudgetViewModel
){

    val states by budgetViewModel.budgetStates.collectAsStateWithLifecycle()
    val events =  budgetViewModel.budgetValidationEvents
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        events.collect { event ->
            when (event) {
                is AddTransactionViewModel.AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage, Toast.LENGTH_SHORT).show()
                }
                AddTransactionViewModel.AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Budget Successfully Added", Toast.LENGTH_LONG).show()
                    navController.navigate(route = Screens.HomePageScreen.routes)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Budget",
                showBackButton = true,
                showMenu = false,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    ){paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {

            MonthSelector(
                state = states,
                onEvent = budgetViewModel::onEvent,
                context = context
            )

            OutlinedTextField(
                value = states.budget,
                onValueChange = {
                    budgetViewModel.onEvent(BudgetEvents.ChangeBudget(it))
                }, // No direct editing, only display
                readOnly = !states.budgetEditState,
                label = { Text("budget") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            budgetViewModel.onEvent(BudgetEvents.ChangeBudgetEditState(state = true))
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Change Budget")
                    }
                },
                leadingIcon = {
                    Text(text = states.budgetCurrencySymbol)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    budgetViewModel.onEvent(BudgetEvents.SaveBudget)
                    budgetViewModel.onEvent(BudgetEvents.ChangeBudgetEditState(state = false))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }

    }
}