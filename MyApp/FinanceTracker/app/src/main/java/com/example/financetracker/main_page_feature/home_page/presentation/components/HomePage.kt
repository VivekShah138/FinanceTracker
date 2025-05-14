package com.example.financetracker.main_page_feature.home_page.presentation.components

import AccountBalance
import BottomNavigationBar
import ExpenseIncomeCards
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.MenuItems
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageEvents
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageViewModel
import com.example.financetracker.main_page_feature.settings.presentation.SettingViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomePageScreen(
    viewModel: HomePageViewModel,
    navController: NavController,
    settingViewModel: SettingViewModel
){

    val states by viewModel.homePageStates.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        settingViewModel.loadUserProfileIfReady()
        Log.d("HomePage","function called")
    }


    MaterialTheme {

        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Home Page",
                    showMenu = true,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = listOf<MenuItems>(
                        MenuItems(
                            text = "Settings",
                            onClick = {
                                navController.navigate(route = Screens.SettingScreen.routes )
                            }
                        ),
                        MenuItems(
                            text = "Logout",
                            onClick = {
                                viewModel.onEvent(HomePageEvents.Logout)
                                navController.navigate(route = Screens.StartUpPageScreen.routes )
                            }
                        )
                    )
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) {InnerPadding ->

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(InnerPadding),
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
                                        navController.navigate(route = "${Screens.ViewRecordsScreen.routes}/0")
                                    },
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            BudgetProgressBar(
                                spentAmount = states.expenseAmount.toFloat(),
                                totalBudget = states.monthlyBudget.toFloat(),
                                sliderAlert = states.receiveAlert,
                                displayText = "Overall"
                            )

                            states.expenseDataWithCategory.forEach{ category ->

                                BudgetProgressBar(
                                    spentAmount = category.value.toFloat(),
                                    totalBudget = states.monthlyBudget.toFloat(),
                                    sliderAlert = states.receiveAlert,
                                    displayText = category.key
                                )

                            }

                        }
                    }
                }
            }



//            Column(modifier = Modifier
//                .fillMaxSize()
//                .padding(InnerPadding),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ){
//
//                AccountBalance(
//                    currencySymbol = states.currencySymbol,
//                    amount = states.accountBalance
//                )
//
//                ExpenseIncomeCards(
//                    expenseAmount = states.expenseAmount,
//                    incomeAmount = states.incomeAmount,
//                    incomeSymbol = states.currencySymbol,
//                    expenseSymbol = states.currencySymbol
//                )
//
//
//                BudgetProgressBar(
//                    spentAmount = states.expenseAmount.toFloat(),
//                    totalBudget = states.monthlyBudget.toFloat(),
//                    sliderAlert = states.receiveAlert,
//                    displayText = "Overall"
//                )
//
//            }
        }
    }
}