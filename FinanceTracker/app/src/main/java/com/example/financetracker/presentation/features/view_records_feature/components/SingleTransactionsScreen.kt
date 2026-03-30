package com.example.financetracker.presentation.features.view_records_feature.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.domain.model.Currency
import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.presentation.features.view_records_feature.events.ViewTransactionsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewTransactionsStates
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewTransactionsViewModel
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun SingleTransactionRoot(
    navController: NavController,
    viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel(),
    transactionId: Int
){
    val states by viewTransactionsViewModel.viewTransactionStates.collectAsStateWithLifecycle()
    val singleItemState by viewTransactionsViewModel.selectedItem.collectAsStateWithLifecycle()

    SingleTransactionScreen(
        navController = navController,
        transactionId = transactionId,
        states = states,
        singleItemState = singleItemState,
        onEvent = viewTransactionsViewModel::onEvent
    )
}
@Composable
fun SingleTransactionScreen(
    navController: NavController,
    states: ViewTransactionsStates,
    singleItemState: Transactions?,
    onEvent: (ViewTransactionsEvents) -> Unit,
    transactionId: Int
) {



    LaunchedEffect(transactionId) {
        onEvent(ViewTransactionsEvents.GetSingleTransaction(transactionId))
    }


    Log.d("ViewTransactionsViewModelS","transaction Single $singleItemState")
    val baseCurrency: Map<String, Currency> = states.baseCurrency

    if(singleItemState != null){


        val transactionCurrencySymbol = singleItemState.currency?.entries?.firstOrNull()?.value?.symbol ?: "$"
        val transactionCurrencyCode = singleItemState.currency?.entries?.firstOrNull()?.key ?: "USD"
        val baseCurrencySymbol = baseCurrency.entries.firstOrNull()?.value?.symbol ?: "£"
        val baseCurrencyCode = baseCurrency.entries.firstOrNull()?.key ?: "GPB"
        val amount = singleItemState.amount
        val transactionName = singleItemState.transactionName
        val formattedDateTime = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            .format(Date(singleItemState.dateTime))
        val transactionType = singleItemState.transactionType
        val transactionCategory = singleItemState.category
        val convertedAmount = singleItemState.convertedAmount
        val exchangeRate = singleItemState.exchangeRate
        val transactionDescription = singleItemState.description
        val cloudSync = singleItemState.cloudSync


        Scaffold(

            topBar = {
                AppTopBar(
                    title = "Transaction Details",
                    showBackButton = true,
                    showMenu = false,
                    onBackClick = {
                        navController.popBackStack()
                        if(states.isSelectionMode){
                            onEvent(ViewTransactionsEvents.ExitSelectionMode)
                        }
                    },
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary,
                )
            },
            containerColor = Color.Transparent,

        ) {paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {


                if(states.customDeleteAlertBoxState){
                    DeleteConfirmationDialog(
                        onDismiss = {
                            onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
                        },
                        onConfirm = {
                            onEvent(ViewTransactionsEvents.DeleteSelectedTransactions)
                            onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
                            navController.navigate(Screens.ViewRecordsScreen(tabIndex = 0))
                        }
                    )
                }



                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight * 1f / 3f)
                            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                            .background(
                                color = MaterialTheme.colorScheme.secondary
                            )

                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = String.format(Locale.US, "$baseCurrencySymbol%.2f", amount),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 40.sp
                                ),
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = transactionName,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp
                                ),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Text(
                                text = formattedDateTime,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 18.sp
                                ),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }

                    }

                    TransactionDetailsCard(
                        transactionType = transactionType,
                        transactionCategory = transactionCategory,
                        convertedAmount = convertedAmount,
                        transactionCurrencySymbol = transactionCurrencySymbol,
                        baseCurrencyCode = baseCurrencyCode,
                        transactionDescription = transactionDescription,
                        cloudSync = cloudSync,
                        exchangeRate = exchangeRate,
                        transactionCurrencyCode = transactionCurrencyCode,
                        screenHeight = screenHeight
                    )


                }

                Button(
                    onClick = {
                        onEvent(
                            ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Delete")
                }
            }
        }

    }
    else{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "With Exchange Rate")
@Composable
fun SingleTransactionPreview() {
    val transaction = Transactions(
        transactionType = "Expense",
        category = "Food",
        currency = emptyMap(),
        amount = 50.0,
        exchangeRate = 1.1,
        convertedAmount = 55.0,
        transactionName = "Dinner at Restaurant",
        description = "Friday night dinner with friends",
        dateTime = 11234567,
        cloudSync = true,
        userUid = "",
        isRecurring = false
    )

    FinanceTrackerTheme(
        dynamicColor = false
    ) {
        SingleTransactionScreen(
            navController = rememberNavController(),
            states = ViewTransactionsStates(),
            singleItemState = transaction,
            onEvent = {

            },
            transactionId = 1,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true,name = "Without Exchange Rate")
@Composable
fun SingleTransactionPreview2() {
    val transaction = Transactions(
        transactionType = "Income",
        category = "Salary",
        currency = emptyMap(),
        amount = 3000.0,
        exchangeRate = null,
        convertedAmount = null,
        transactionName = "Monthly Salary",
        description = null,
        dateTime = 11234567,
        cloudSync = false,
        userUid = "",
        isRecurring = false
    )

    FinanceTrackerTheme(
        dynamicColor = false
    ) {
        SingleTransactionScreen(
            navController = rememberNavController(),
            states = ViewTransactionsStates(),
            singleItemState = transaction,
            onEvent = {

            },
            transactionId = 1,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true,name = "Is Null")
@Composable
fun SingleTransactionPreview3() {
    FinanceTrackerTheme(
        dynamicColor = false
    ) {
        SingleTransactionScreen(
            navController = rememberNavController(),
            states = ViewTransactionsStates(),
            singleItemState = null,
            onEvent = {

            },
            transactionId = 1,
        )
    }
}


