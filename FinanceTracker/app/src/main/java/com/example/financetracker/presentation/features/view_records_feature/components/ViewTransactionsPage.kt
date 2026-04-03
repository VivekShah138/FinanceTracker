package com.example.financetracker.presentation.features.view_records_feature.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.finance_entry_feature.components.TransactionItemCard
import com.example.financetracker.presentation.features.view_records_feature.events.ViewTransactionsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewTransactionsStates
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlin.Int
import kotlin.collections.Set


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewTransactionsPage(
    navController: NavController,
    onEvent: (ViewTransactionsEvents) -> Unit,
    states: ViewTransactionsStates
) {


    BackHandler(enabled = states.isSelectionMode) {
        onEvent(ViewTransactionsEvents.ExitSelectionMode)
    }


    if (states.customDeleteAlertBoxState) {
        DeleteConfirmationDialog(
            onDismiss = {
                onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
            },
            onConfirm = {
                onEvent(ViewTransactionsEvents.DeleteSelectedTransactions)
                onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
            }
        )
    }

    Surface {
        FilterBottomSheetModal(
            showSheet = states.filterBottomSheetState,
            onDismiss = {
                onEvent(
                    ViewTransactionsEvents.SelectTransactionsFilter(state = false)
                )
            },
            filters = states.filters,
            onApply = {
                onEvent(
                    ViewTransactionsEvents.ApplyFilter
                )
                onEvent(
                    ViewTransactionsEvents.SelectTransactionsFilter(state = false)
                )
            },
            onFilterChange = { filter ->
                onEvent(
                    ViewTransactionsEvents.UpdateFilter(filter = filter)
                )
            },
            onClearAll = {
                onEvent(
                    ViewTransactionsEvents.ClearFilter(duration = states.selectedDuration)
                )
                onEvent(
                    ViewTransactionsEvents.SelectTransactionsFilter(state = false)
                )
            },
            allCategories = states.categories,
            applyFilterVisibility = states.filterApplyButton
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            TransactionsTotal(
                currencySymbol = states.currencySymbol,
                amount = states.totalAmount
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    DateFilterWithIcon(
                        onFilterIconClick = {
                            onEvent(
                                ViewTransactionsEvents.SelectTransactionsFilter(state = true)
                            )
                            if (states.isSelectionMode) {
                                onEvent(ViewTransactionsEvents.ExitSelectionMode)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (states.transactionsList.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize().height(350.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No transactions available")
                        }
                    } else {
                        states.transactionsList.forEachIndexed { index, transaction ->
                            val isSelected = states.selectedTransactions.contains(transaction.transactionId)
                            Column {
                                TransactionItemCard(
                                    item = transaction,
                                    isSelected = isSelected,
                                    onClick = {
                                        if (states.isSelectionMode) {
                                            onEvent(
                                                ViewTransactionsEvents.ToggleTransactionSelection(
                                                    transaction.transactionId!!
                                                )
                                            )
                                        } else {
                                            onEvent(
                                                ViewTransactionsEvents.SelectItems(
                                                    transactions = transaction
                                                )
                                            )

                                            val transactionId = transaction.transactionId
                                            if(transactionId != null){
                                                navController.navigate(Screens.SingleTransactionScreen(transactionId = transactionId))
                                            }
                                        }
                                    },
                                    onLongClick = {
                                        if (!states.isSelectionMode) {
                                            onEvent(ViewTransactionsEvents.EnterSelectionMode)
                                            onEvent(
                                                ViewTransactionsEvents.ToggleTransactionSelection(
                                                    transaction.transactionId!!
                                                )
                                            )
                                            onEvent(
                                                ViewTransactionsEvents.SelectItems(transactions = transaction)
                                            )
                                        }
                                    }
                                )
                                if (index < states.transactionsList.lastIndex) {
                                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp),color = MaterialTheme.colorScheme.inverseSurface)
                                }
                            }
                        }
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
fun ViewTransactionsPagePreview(){
    FinanceTrackerTheme(dynamicColor = false, darkTheme = true) {
        ViewTransactionsPage(
            navController = rememberNavController(),
            onEvent = {

            },
            states = ViewTransactionsStates(
                transactionsList = listOf(
                    Transactions(
                        transactionId = 1,
                        amount = 1.45,
                        currency = null,
                        convertedAmount = 0.0,
                        exchangeRate = 0.0,
                        transactionType = "Expense",
                        category = "Groceries",
                        dateTime = 1744486763759,
                        userUid = "He",
                        description = "ItemDescription",
                        isRecurring = false,
                        cloudSync = false,
                        transactionName = "Milk"
                    ),
                    Transactions(
                        transactionId = 2,
                        amount = 1.45,
                        currency = null,
                        convertedAmount = 0.0,
                        exchangeRate = 0.0,
                        transactionType = "Expense",
                        category = "Groceries",
                        dateTime = 1744486763759,
                        userUid = "He",
                        description = "ItemDescription",
                        isRecurring = false,
                        cloudSync = false,
                        transactionName = "Milk"
                    ),
                    Transactions(
                        transactionId = 3,
                        amount = 1.45,
                        currency = null,
                        convertedAmount = 0.0,
                        exchangeRate = 0.0,
                        transactionType = "Expense",
                        category = "Groceries",
                        dateTime = 1744486763759,
                        userUid = "He",
                        description = "ItemDescription",
                        isRecurring = false,
                        cloudSync = false,
                        transactionName = "Milk"
                    ),
                    Transactions(
                        transactionId = 4,
                        amount = 228.8,
                        currency = null,
                        convertedAmount = 0.0,
                        exchangeRate = 0.0,
                        transactionType = "Income",
                        category = "Groceries",
                        dateTime = 1744486763759,
                        userUid = "He",
                        description = "ItemDescription",
                        isRecurring = false,
                        cloudSync = false,
                        transactionName = "Salary"
                    )
                ),
                selectedTransactions = setOf(4),
                totalAmount = 100.0
            )
        )
    }
}