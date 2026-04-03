package com.example.financetracker.presentation.features.home_feature.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.features.budget_feature.components.NoBudgetMessage
import com.example.financetracker.utils.MenuItems
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.home_feature.HomePageEvents
import com.example.financetracker.presentation.features.home_feature.HomeScreenStates
import com.example.financetracker.presentation.features.home_feature.HomePageViewModel
import com.example.financetracker.presentation.features.settings_feature.core_settings.SettingsViewModel
import com.example.financetracker.ui.theme.FinanceTrackerTheme


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeRoot(
    viewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController,
    settingsViewModel: SettingsViewModel
){
    val states by viewModel.homeScreenStates.collectAsStateWithLifecycle()

    HomeScreen(
        navController = navController,
        states = states,
        loadUserProfileIfReady = settingsViewModel::loadUserProfileIfReady,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun HomeScreen(
    navController: NavController,
    states: HomeScreenStates,
    loadUserProfileIfReady: () -> Unit,
    onEvent: (HomePageEvents) -> Unit
){
    LaunchedEffect(Unit) {
        loadUserProfileIfReady()
    }


    MaterialTheme {

        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Home Page",
                    showMenu = true,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = listOf(
                        MenuItems(
                            text = "View Budget",
                            onClick = {
                                navController.navigate(route = Screens.BudgetScreen)
                            }
                        ),
                        MenuItems(
                            text = "Logout",
                            onClick = {
                               onEvent(HomePageEvents.Logout)
                                navController.navigate(route = Screens.StartUpPageScreen)
                            }
                        )
                    )
                )
            }
        ) {innerPadding ->

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                item {
                    AccountBalance(
                        currencySymbol = states.currencySymbol,
                        amount = states.accountBalance
                    )

                    ExpenseIncomeCards(
                        expenseAmount = states.expenseAmount,
                        incomeAmount = states.incomeAmount,
                        incomeSymbol = states.currencySymbol,
                        expenseSymbol = states.currencySymbol
                    )
                }

                item {

                    Surface(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        tonalElevation = 2.dp,
                        shape = MaterialTheme.shapes.medium,
                        shadowElevation = 4.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Column {

                            Row (
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Text(
                                    text = "Spending Usage",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = "Details",
                                    style = MaterialTheme.typography.titleSmall,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.clickable {
                                        navController.navigate(route = Screens.ViewRecordsScreen(tabIndex = 0))
                                    },
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                if(states.budgetExist){
                                    Column {
                                        BudgetProgressBar(
                                            spentAmount = states.expenseAmount.toFloat(),
                                            totalBudget = states.monthlyBudget.toFloat(),
                                            displayText = "Overall"
                                        )

                                        states.expenseDataWithCategory.forEach{ category ->

                                            BudgetProgressBar(
                                                spentAmount = category.value.toFloat(),
                                                totalBudget = states.monthlyBudget.toFloat(),
                                                displayText = category.key
                                            )
                                        }
                                    }
                                }else{
                                    Column {
                                        NoBudgetMessage()
                                        Button(
                                            onClick = {
                                                navController.navigate(Screens.BudgetScreen)
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                        ) {
                                            Text(
                                                text = "Set Budget"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    FinanceTrackerTheme {
        HomeScreen(
            states = HomeScreenStates(),
            loadUserProfileIfReady = {

            },
            onEvent = {

            },
            navController = rememberNavController()
        )
    }
}