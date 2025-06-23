package com.example.financetracker.main_page_feature.home_page.presentation.components

import AccountBalance
import BottomNavigationBar
import ExpenseIncomeCards
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.ui.theme.AppTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun HomePagePreviewScreen(){

    val categoryApp: Map<String, Double> = mapOf(
        "Food" to 120.50,
        "Transport" to 45.00,
        "Rent" to 800.00,
        "Utilities" to 95.75,
        "Entertainment" to 60.00,
        "Health" to 30.25
    )


    AppTheme (darkTheme = true) {
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
//            bottomBar = {
////                BottomNavigationBar()
////            }

        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                item {
                    AccountBalance(
                        currencySymbol = "$",
                        amount = "2,500.00"
                    )

                    ExpenseIncomeCards(
                        expenseAmount = "100.00",
                        incomeAmount = "800.00000"
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
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){

                                Text(
                                    text = "Spending Usage",
                                    style = MaterialTheme.typography.titleLarge,
//                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = "Details",
                                    style = MaterialTheme.typography.titleSmall,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.clickable {

                                    },
                                    color = MaterialTheme.colorScheme.primary
                                )

//                                TextButton(
//                                    onClick = {
//
//                                    },
//                                    modifier = Modifier.padding(0.dp)
//                                ) {
//
//                                    Text(
//                                        text = "Details",
//                                        style = MaterialTheme.typography.titleSmall,
//                                        textAlign = TextAlign.End,
//                                        modifier = Modifier.padding(0.dp)
//                                    )
//                                }
                            }


                            BudgetProgressBar(
                                spentAmount = 80f,
                                totalBudget = 130f,
                                sliderAlert = 80f,
                                displayText = "Overall"
                            )
//
                            categoryApp.forEach{category ->

                                BudgetProgressBar(
                                    spentAmount = category.value.toFloat(),
                                    totalBudget = 130f,
                                    sliderAlert = 80f,
                                    displayText = category.key
                                )

                            }

                        }
                    }
                }
            }
        }
    }
}

