package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components.TransactionItemCard
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsViewModel

@Composable
fun ViewTransactionsPage(
    navController: NavController,
    viewModel: ViewTransactionsViewModel
){
    var sortOrder by remember { mutableStateOf("Ascending") }
    var type by remember { mutableStateOf("Income") }
    var selectedCategories by remember { mutableStateOf(listOf<String>()) }

    val allCategories = listOf("Food", "Transport", "Shopping", "Bills", "Other")



    val states by viewModel.viewTransactionStates.collectAsStateWithLifecycle()

    BackHandler(enabled = states.isSelectionMode) {
        viewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
    }


    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        DateFilterWithIcon(
            selectedDuration = states.selectedDuration,
            rangeDropDownExpanded = states.rangeDropDownExpanded,
            onDurationSelected = {selectedDuration ->
                viewModel.onEvent(
                    ViewTransactionsEvents.SelectTransactionsDuration(
                        duration = selectedDuration,
                        expanded = false
                    )
                )
            },
            onFilterIconClick = {
                viewModel.onEvent(
                    ViewTransactionsEvents.SelectTransactionsFilter(state = true)
                )
                if(states.isSelectionMode){
                    viewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
                }
            },
            filterOptions = states.durationRange,
            onRangeDropDownClick = {
                viewModel.onEvent(
                    ViewTransactionsEvents.SelectTransactionsDuration(
                        duration = states.selectedDuration,
                        expanded = true
                    )
                )
                if(states.isSelectionMode){
                    viewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
                }
            },
            onRangeDropDownDismiss = {
                viewModel.onEvent(
                    ViewTransactionsEvents.SelectTransactionsDuration(
                        duration = states.selectedDuration,
                        expanded = false
                    )
                )
            }
        )


        Surface {
            FilterBottomSheetModal(
                showSheet = states.filterBottomSheetState,
                onDismiss = {
                    viewModel.onEvent(
                        ViewTransactionsEvents.SelectTransactionsFilter(state = false)
                    )
                },
                sortOrder = sortOrder,
                onSortOrderChange = { sortOrder = it },
                type = type,
                onTypeChange = { type = it },
                selectedCategories = selectedCategories,
                onCategoryToggle = { category ->
                    selectedCategories = if (selectedCategories.contains(category)) {
                        selectedCategories - category
                    } else {
                        selectedCategories + category
                    }
                },
                allCategories = allCategories
            )
        }

        LazyColumn {
            when(states.selectedDuration){
                "Today" -> {
                    viewModel.onEvent(
                        ViewTransactionsEvents.LoadTransactionsToday
                    )
                }
                "This Month" -> {
                    viewModel.onEvent(
                        ViewTransactionsEvents.LoadTransactionsThisMonth
                    )
                }
                "Last Month" -> {
                    viewModel.onEvent(
                        ViewTransactionsEvents.LoadTransactionsLastMonth
                    )
                }
                "Last 3 Months" -> {
                    viewModel.onEvent(
                        ViewTransactionsEvents.LoadTransactionsLast3Month
                    )
                }
                "Custom Range" -> {
                    viewModel.onEvent(
                        ViewTransactionsEvents.ChangeCustomDateAlertBox(state = true)
                    )
                }
            }

            items(states.transactionsList){ transaction ->



                val isSelected = states.selectedTransactions.contains(transaction.transactionId)

                TransactionItemCard(
                    item = transaction,
                    isSelected = isSelected,
                    isSelectionMode = states.isSelectionMode,
                    onClick = {
                        if (states.isSelectionMode) {
                            viewModel.onEvent(ViewTransactionsEvents.ToggleTransactionSelection(transaction.transactionId!!))
                        } else {
                            // Normal click action
                        }
                    },
                    onLongClick = {
                        if (!states.isSelectionMode) {
                            viewModel.onEvent(ViewTransactionsEvents.EnterSelectionMode)
                            viewModel.onEvent(ViewTransactionsEvents.ToggleTransactionSelection(transaction.transactionId!!))
                        }
                    }
                )
            }
        }
    }
}

