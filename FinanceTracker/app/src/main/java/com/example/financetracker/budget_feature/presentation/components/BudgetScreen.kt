package com.example.financetracker.budget_feature.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.budget_feature.presentation.BudgetEvents
import com.example.financetracker.budget_feature.presentation.BudgetStates
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
    val singleBudgetState by budgetViewModel.singleBudgetState.collectAsStateWithLifecycle()
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Surface(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.90f).padding(vertical = 8.dp)
                    .padding(horizontal = 16.dp),
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MonthSelectorBudget(
                        state = states,
                        onEvent = budgetViewModel::onEvent,
                        context = context
                    )
                }


                if (states.createBudgetState) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        NoBudgetMessage()
                    }

                } else {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            BudgetAmountInput(
                                amount = states.budget,
                                currencySymbol = states.budgetCurrencySymbol,
                                onAmountChange = {
                                    budgetViewModel.onEvent(BudgetEvents.ChangeBudget(it))
                                }
                            )

                            Spacer(Modifier.height(10.dp))


                            ReceiveAlertSwitch(
                                text = "Receive Alert",
                                isCheck = states.receiveAlerts,
                                onCheckChange = {
                                    budgetViewModel.onEvent(
                                        BudgetEvents.ChangeReceiveBudgetAlerts(it)
                                    )
                                },
                                fontSize = 24.sp
                            )

                            Spacer(Modifier.height(15.dp))

                            if(states.receiveAlerts){
                                SliderWithValueInsideCustomThumb(
                                    sliderPosition = states.alertThresholdPercent,
                                    onValueChange = {
                                        budgetViewModel.onEvent(BudgetEvents.ChangeAlertThresholdAmount(it))
                                    }
                                )
                            }

                        }
                    }

                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {

                        if(states.createBudgetState){
                            budgetViewModel.onEvent(BudgetEvents.ChangeCreateBudgetState(state = false))
                        }else{
                            budgetViewModel.onEvent(BudgetEvents.SaveBudget)
                            budgetViewModel.onEvent(BudgetEvents.ChangeCreateBudgetState(state = false))
                        }

                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    if(states.createBudgetState){
                        Text("Create")
                    }else{
                        Text("Save")
                    }
                }
            }
        }
    }
}