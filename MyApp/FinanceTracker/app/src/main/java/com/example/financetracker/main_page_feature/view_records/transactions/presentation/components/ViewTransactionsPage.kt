package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components.TransactionItemCard
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsViewModel
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter

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


    if (states.customDateAlertBoxState) {
        CustomDateRangeBottomSheet(
            bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismiss = {
                viewModel.onEvent(ViewTransactionsEvents.ChangeCustomDateAlertBox(state = false))
            },
            onDateRangeSelected = { fromDate, toDate ->
                viewModel.onEvent(
                    ViewTransactionsEvents.SelectTransactionsDuration(
                        duration = DurationFilter.CustomRange(from = fromDate, to = toDate),
                        expanded = false
                    )
                )
            }
        )
        }


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TransactionsTotal(
                currencySymbol = states.currencySymbol,
                amount = states.totalAmount
            )

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

            LazyColumn {
                items(states.transactionsList) { transaction ->

                    val isSelected = states.selectedTransactions.contains(transaction.transactionId)

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
                                // Normal click action
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
                            }
                        }
                    )
                }
            }
        }
}

