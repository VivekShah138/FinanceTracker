package com.example.financetracker.presentation.features.view_records_feature.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.utils.MenuItems
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.view_records_feature.events.ViewSavedItemsEvents
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewSavedItemsViewModel
import com.example.financetracker.presentation.features.view_records_feature.events.ViewTransactionsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewSavedItemsStates
import com.example.financetracker.presentation.features.view_records_feature.states.ViewTransactionsStates
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewTransactionsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun RecordsRoot(
    navController: NavController,
    viewTransactionsViewModel: ViewTransactionsViewModel = hiltViewModel(),
    viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel(),
    defaultTabIndex: Int = 0
){
    val viewTransactionsStates by viewTransactionsViewModel.viewTransactionStates.collectAsStateWithLifecycle()
    val viewSavedItemsStates by viewSavedItemsViewModel.viewSavedItemsStates.collectAsStateWithLifecycle()

    RecordsScreen(
        navController = navController,
        viewTransactionsStates = viewTransactionsStates,
        viewSavedItemsStates = viewSavedItemsStates,
        viewSavedItemsEvents = viewSavedItemsViewModel::onEvent,
        viewTransactionsEvents = viewTransactionsViewModel::onEvent,
        defaultTabIndex = defaultTabIndex,
        savedItemsValidationEvent = viewSavedItemsViewModel.savedItemsValidationEvents
    )
}

@Composable
fun RecordsScreen(
    navController: NavController,
    viewTransactionsStates: ViewTransactionsStates,
    viewSavedItemsStates: ViewSavedItemsStates,
    viewTransactionsEvents: (ViewTransactionsEvents) -> Unit,
    viewSavedItemsEvents: (ViewSavedItemsEvents) -> Unit,
    defaultTabIndex: Int,
    savedItemsValidationEvent: Flow<AddTransactionViewModel.AddTransactionValidationEvent>
) {

    val pagerState = rememberPagerState(
        initialPage = defaultTabIndex,
        pageCount = {2}
    )

    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            if(viewTransactionsStates.isSelectionMode){
                AppTopBar(
                    title = viewTransactionsStates.selectedTransactions.size.toString(),
                    showMenu = true,
                    showBackButton = true,
                    onBackClick = {
                        viewTransactionsEvents(ViewTransactionsEvents.ExitSelectionMode)
                    },
                    menuItems = if(viewTransactionsStates.selectedTransactions.size == 1){
                        listOf(
                            MenuItems(
                                text = "Info",
                                onClick = {
                                    val transactionId = viewTransactionsStates.selectedTransactions.firstOrNull()

                                    if (transactionId != null) {
                                        navController.navigate(Screens.SingleTransactionScreen(transactionId))
                                    }

                                }
                            ),
                            MenuItems(
                                text = "Delete",
                                onClick = {
                                    viewTransactionsEvents(
                                        ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewTransactionsEvents(ViewTransactionsEvents.SelectAllTransactions)
                                }
                            )
                        )
                    }
                    else{
                        listOf(
                            MenuItems(
                                text = "Delete All",
                                onClick = {
                                    viewTransactionsEvents(
                                        ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewTransactionsEvents(ViewTransactionsEvents.SelectAllTransactions)
                                }
                            )
                        )
                    }
                )
            }
            else if(viewSavedItemsStates.isSelectionMode){
                AppTopBar(
                    title = viewSavedItemsStates.selectedSavedItems.size.toString(),
                    showMenu = true,
                    showBackButton = true,
                    onBackClick = {
                        viewSavedItemsEvents(
                            ViewSavedItemsEvents.ExitSelectionMode
                        )
                    },
                    menuItems = if(viewSavedItemsStates.selectedSavedItems.size == 1){
                        listOf<MenuItems>(
                            MenuItems(
                                text = "Info",
                                onClick = {
                                    val savedItemId = viewSavedItemsStates.selectedSavedItems.firstOrNull()

                                    if (savedItemId != null) {
                                        navController.navigate(Screens.SingleTransactionScreen(savedItemId))
                                    }
                                }
                            ),
                            MenuItems(
                                text = "Delete",
                                onClick = {
                                    viewSavedItemsEvents(
                                        ViewSavedItemsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Edit",
                                onClick = {
                                    viewSavedItemsEvents(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = true))
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewSavedItemsEvents(ViewSavedItemsEvents.SelectAllItems)
                                }
                            )
                        )
                    }
                    else{
                        listOf(
                            MenuItems(
                                text = "Delete All",
                                onClick = {
                                    viewSavedItemsEvents(
                                        ViewSavedItemsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewSavedItemsEvents(ViewSavedItemsEvents.SelectAllItems)
                                }
                            )
                        )
                    }
                )
            }
            else{
                AppTopBar(
                    title = "Records",
                    showMenu = false,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = emptyList()
                )
            }

            RecordsTopBar(
                onNavigateToTransactionInfo = {
                    val transactionId = viewTransactionsStates.selectedTransactions.firstOrNull()
                    if (transactionId != null) {
                        navController.navigate(Screens.SingleTransactionScreen(transactionId))
                    }
                },
                onNavigateToSavedItemInfo = {
                    val savedItemId = viewSavedItemsStates.selectedSavedItems.firstOrNull()
                    if (savedItemId != null) {
                        navController.navigate(Screens.SingleTransactionScreen(savedItemId))
                    }
                },
                transactionsState = viewTransactionsStates,
                savedItemsState = viewSavedItemsStates,
                viewTransactionsEvents = viewTransactionsEvents,
                viewSavedItemsEvents = viewSavedItemsEvents
            )

        },

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LaunchedEffect(pagerState.currentPage) {
                if (pagerState.currentPage == 0 && viewSavedItemsStates.isSelectionMode) {
                    viewSavedItemsEvents(ViewSavedItemsEvents.ExitSelectionMode)
                }
                if (pagerState.currentPage == 1 && viewTransactionsStates.isSelectionMode) {
                    viewTransactionsEvents(ViewTransactionsEvents.ExitSelectionMode)
                }
            }


            TabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                            if (viewSavedItemsStates.isSelectionMode) {
                                viewSavedItemsEvents(ViewSavedItemsEvents.ExitSelectionMode)
                            }
                        }
                    },
                    text = { Text("Transactions") }
                )
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                            if (viewTransactionsStates.isSelectionMode) {
                                viewTransactionsEvents(ViewTransactionsEvents.ExitSelectionMode)
                            }
                        }
                    },
                    text = { Text("Saved Items") }
                )
            }


                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> ViewTransactionsPage(
                            navController = navController,
                            onEvent = viewTransactionsEvents,
                            states = viewTransactionsStates
                        )
                        1 -> ViewSavedItemsPage(
                            navController = navController,
                            states = viewSavedItemsStates,
                            savedItemsValidationEvent = savedItemsValidationEvent,
                            onEvent = viewSavedItemsEvents
                        )
                    }
                }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun RecordsPagePreview(){
    RecordsScreen(
        navController = rememberNavController(),
        viewTransactionsStates = ViewTransactionsStates(),
        viewSavedItemsStates = ViewSavedItemsStates(),
        viewTransactionsEvents = {

        },
        viewSavedItemsEvents = {

        },
        defaultTabIndex = 0,
        savedItemsValidationEvent = emptyFlow()
    )
}
