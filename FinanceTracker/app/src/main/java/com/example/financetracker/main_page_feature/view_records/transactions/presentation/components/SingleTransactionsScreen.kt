package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SingleTransactionScreen(
    navController: NavController,
    viewTransactionsViewModel: ViewTransactionsViewModel,
    transactionId: Int
) {

    val states by viewTransactionsViewModel.viewTransactionStates.collectAsStateWithLifecycle()
    val singleItemState by viewTransactionsViewModel.selectedItem.collectAsStateWithLifecycle()

    LaunchedEffect(transactionId) {
        viewTransactionsViewModel.onEvent(ViewTransactionsEvents.GetSingleTransaction(transactionId))
    }
    val transaction = singleItemState


    Log.d("ViewTransactionsViewModelS","transaction Single ${transaction}")
    val baseCurrency: Map<String, com.example.financetracker.setup_account.domain.model.Currency> = states.baseCurrency

    if(transaction != null){


        val transactionCurrencySymbol = transaction.currency?.entries?.firstOrNull()?.value?.symbol ?: "$"
        val transactionCurrencyCode = transaction.currency?.entries?.firstOrNull()?.key ?: "USD"
        val baseCurrencySymbol = baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "£"
        val baseCurrencyCode = baseCurrency?.entries?.firstOrNull()?.key ?: "GPB"
        val amount = transaction.amount
        val transactionName = transaction.transactionName
        val formattedDateTime = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            .format(Date(transaction.dateTime))
        val transactionType = transaction.transactionType
        val transactionCategory = transaction.category
        val convertedAmount = transaction.convertedAmount
        val exchangeRate = transaction.exchangeRate
        val transactionDescription = transaction.description
        val cloudSync = transaction.cloudSync


        Scaffold(

            topBar = {
                AppTopBar(
                    title = "Transaction Details",
                    showBackButton = true,
                    showMenu = false,
                    onBackClick = {
                        navController.popBackStack()
                        if(states.isSelectionMode){
                            viewTransactionsViewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
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
                            viewTransactionsViewModel.onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
                        },
                        onConfirm = {
                            viewTransactionsViewModel.onEvent(ViewTransactionsEvents.DeleteSelectedTransactions)
                            viewTransactionsViewModel.onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
                            navController.navigate("${Screens.ViewRecordsScreen.routes}/0")

                        },
                        showDialog = states.customDeleteAlertBoxState
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
                        viewTransactionsViewModel.onEvent(
                            ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                        )
//                        navController.navigate("${Screens.ViewRecordsScreen.routes}/0")
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Delete")
                }
            }





//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    .background(MaterialTheme.colorScheme.background)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 20.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = String.format(Locale.US, "$baseCurrencySymbol%.2f", amount),
//                        style = MaterialTheme.typography.headlineLarge.copy(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 40.sp
//                        ),
//                        color = MaterialTheme.colorScheme.onBackground,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = transactionName,
//                        style = MaterialTheme.typography.headlineSmall.copy(
//                            fontWeight = FontWeight.Medium,
//                            fontSize = 18.sp
//                        ),
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                    Text(
//                        text = formattedDateTime,
//                        style = MaterialTheme.typography.headlineSmall.copy(
//                            fontWeight = FontWeight.Medium,
//                            fontSize = 18.sp
//                        ),
//                        color = Color.Gray
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    TransactionDetailsCard(
//                        transactionType = transactionType,
//                        transactionCategory = transactionCategory,
//                        convertedAmount = convertedAmount,
//                        transactionCurrencySymbol = transactionCurrencySymbol,
//                        baseCurrencyCode = baseCurrencyCode,
//                        transactionDescription = transactionDescription,
//                        cloudSync = cloudSync,
//                        exchangeRate = exchangeRate,
//                        transactionCurrencyCode = transactionCurrencyCode,
//                        screenHeight = screenHeight
//
//                    )
//                }
//
//                // Button at the bottom
//                Button(
//                    onClick = {
//                        viewTransactionsViewModel.onEvent(
//                            ViewTransactionsEvents.DeleteSelectedTransactions
//                        )
//                        navController.navigate(Screens.ViewRecordsScreen.routes)
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                        .align(Alignment.BottomCenter),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor = MaterialTheme.colorScheme.onPrimary
//                    )
//                ) {
//                    Text("Delete")
//                }
//            }
        }

    }
    else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

    }
}
