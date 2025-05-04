package com.example.financetracker.main_page_feature.home_page.presentation.components

import AccountBalance
import BottomNavigationBar
import ExpenseIncomeCards
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.financetracker.core.core_presentation.components.AppTopBar

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun HomePagePreviewScreen(){
    MaterialTheme {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Home Page",
                    showMenu = true,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = emptyList()
                )
            },
            bottomBar = {
                BottomNavigationBar()
            }

        ) { padding ->

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                AccountBalance(
                    currencySymbol = "$",
                    amount = "2,500.00"
                )

                ExpenseIncomeCards(
                    expenseAmount = "100.00",
                    incomeAmount = "800.00000"
                )

                BudgetProgressBar(
                    spentAmount = 80f,
                    totalBudget = 130f,
                    sliderAlert = 80f
                )
            }
        }
    }
}

