package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components.TransactionItemCard
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewTransactionsPage(
    navController: NavController,
    viewModel: ViewTransactionsViewModel
) {


    val states by viewModel.viewTransactionStates.collectAsStateWithLifecycle()

    BackHandler(enabled = states.isSelectionMode) {
        viewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
    }


    if (states.customDeleteAlertBoxState) {
        DeleteConfirmationDialog(
            onDismiss = {
                viewModel.onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
            },
            onConfirm = {
                viewModel.onEvent(ViewTransactionsEvents.DeleteSelectedTransactions)
                viewModel.onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
            },
            showDialog = states.customDeleteAlertBoxState
        )
    }

    Surface {
        FilterBottomSheetModal2(
            showSheet = states.filterBottomSheetState,
            onDismiss = {
                viewModel.onEvent(
                    ViewTransactionsEvents.SelectTransactionsFilter(state = false)
                )
            },
            filters = states.filters,
            onApply = {
                viewModel.onEvent(
                    ViewTransactionsEvents.ApplyFilter
                )
                viewModel.onEvent(
                    ViewTransactionsEvents.SelectTransactionsFilter(state = false)
                )
            },
            onFilterChange = { filter ->
                viewModel.onEvent(
                    ViewTransactionsEvents.UpdateFilter(filter = filter)
                )
            },
            onClearAll = {
                viewModel.onEvent(
                    ViewTransactionsEvents.ClearFilter(duration = states.selectedDuration)
                )
                viewModel.onEvent(
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
            Surface(
                modifier = Modifier.fillMaxSize(),
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    DateFilterWithIcon2(
                        onFilterIconClick = {
                            viewModel.onEvent(
                                ViewTransactionsEvents.SelectTransactionsFilter(state = true)
                            )
                            if (states.isSelectionMode) {
                                viewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
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
                                    isSelectionMode = states.isSelectionMode,
                                    onClick = {
                                        if (states.isSelectionMode) {
                                            viewModel.onEvent(
                                                ViewTransactionsEvents.ToggleTransactionSelection(
                                                    transaction.transactionId!!
                                                )
                                            )
                                        } else {
                                            viewModel.onEvent(
                                                ViewTransactionsEvents.SelectItems(
                                                    transactions = transaction
                                                )
                                            )

                                            navController.navigate("${Screens.SingleTransactionScreen.routes}/${transaction.transactionId}")
                                        }
                                    },
                                    onLongClick = {
                                        if (!states.isSelectionMode) {
                                            viewModel.onEvent(ViewTransactionsEvents.EnterSelectionMode)
                                            viewModel.onEvent(
                                                ViewTransactionsEvents.ToggleTransactionSelection(
                                                    transaction.transactionId!!
                                                )
                                            )
                                            viewModel.onEvent(
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

