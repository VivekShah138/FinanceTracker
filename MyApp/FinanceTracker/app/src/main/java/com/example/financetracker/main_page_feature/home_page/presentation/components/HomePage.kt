package com.example.financetracker.main_page_feature.home_page.presentation.components

import AccountBalance
import BottomNavigationBar
import ExpenseIncomeCards
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.MenuItems
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageEvents
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageViewModel



@Composable
fun HomePageScreen(
    viewModel: HomePageViewModel,
    navController: NavController
){

    val states by viewModel.homePageStates.collectAsStateWithLifecycle()


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
                                navController.navigate(route = Screens.LogInScreen.routes )
                            }
                        )
                    )
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) {InnerPadding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(InnerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

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


                BudgetProgressBar(
                    spentAmount = states.expenseAmount.toFloat(),
                    totalBudget = states.monthlyBudget.toFloat()
                )

            }
        }
    }
}