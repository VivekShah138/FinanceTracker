package com.example.financetracker.presentation.features.budget_feature.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.features.budget_feature.BudgetEvents
import com.example.financetracker.presentation.features.budget_feature.BudgetViewModel

import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.budget_feature.BudgetStates
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel.AddTransactionValidationEvent
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BudgetRoot(
    navController: NavController,
    budgetViewModel: BudgetViewModel = hiltViewModel()
){
    val states by budgetViewModel.budgetStates.collectAsStateWithLifecycle()

    BudgetScreen(
        navController = navController,
        states = states,
        onEvent = budgetViewModel::onEvent,
        budgetValidationEvents = budgetViewModel.budgetValidationEvents
    )
}

@Composable
fun BudgetScreen(
    navController: NavController,
    states: BudgetStates,
    onEvent: (BudgetEvents) -> Unit,
    budgetValidationEvents: Flow<AddTransactionValidationEvent>
){
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        budgetValidationEvents.collect { event ->
            when (event) {
                is AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Budget Successfully Added", Toast.LENGTH_LONG).show()
                    navController.navigate(route = Screens.HomePageScreen)
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
                        onEvent = onEvent,
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
                                    onEvent(BudgetEvents.ChangeBudget(it))
                                }
                            )

                            Spacer(Modifier.height(10.dp))


                            ReceiveAlertSwitch(
                                text = "Receive Alert",
                                isCheck = states.receiveAlerts,
                                onCheckChange = {
                                    onEvent(
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
                                        onEvent(BudgetEvents.ChangeAlertThresholdAmount(it))
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
                            onEvent(BudgetEvents.ChangeCreateBudgetState(state = false))
                        }else{
                            onEvent(BudgetEvents.SaveBudget)
                            onEvent(BudgetEvents.ChangeCreateBudgetState(state = false))
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


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BudgetScreenPreview(){
    FinanceTrackerTheme {
        BudgetScreen(
            navController = rememberNavController(),
            states = BudgetStates(),
            onEvent = {

            },
            budgetValidationEvents = emptyFlow()
        )
    }
}