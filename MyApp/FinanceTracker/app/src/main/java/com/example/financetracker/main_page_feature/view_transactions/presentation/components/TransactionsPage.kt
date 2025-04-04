package com.example.financetracker.main_page_feature.view_transactions.presentation.components

import BottomNavigationBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.main_page_feature.view_transactions.presentation.ViewTransactionsViewModel


@Composable
fun TransactionsPage(
    navController: NavController,
    viewModel: ViewTransactionsViewModel
) {

    val states by viewModel.viewTransactionStates.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Transactions",
                showMenu = true,
                showBackButton = false,
                onBackClick = {},
                menuItems = emptyList()
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }

    ) { padding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LazyColumn {
                items(states.transactionsList){ transaction ->


                    val priceColor = if (transaction.transactionType == "Expense") {
                        Color.Red
                    } else {
                        Color.Green
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp), // Adds shadow for better visual depth
                        shape = MaterialTheme.shapes.medium // Rounded corners
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Display the transaction name
                            Text(
                                text = transaction.transactionName,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )

                            // Display the transaction price with the dynamic color
                            Text(
                                text = "${transaction.amount}",
                                color = priceColor,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
