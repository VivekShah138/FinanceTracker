package com.example.financetracker.main_page_feature.view_records.presentation.components

import BottomNavigationBar
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.utils.MenuItems
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsEvents
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsViewModel
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.components.ViewSavedItemsPage
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsViewModel
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.components.ViewTransactionsPage
import kotlinx.coroutines.launch


@Composable
fun RecordsPage(
    navController: NavController,
    viewTransactionsViewModel: ViewTransactionsViewModel,
    viewSavedItemsViewModel: ViewSavedItemsViewModel,
    defaultTabIndex: Int = 0
) {

    val viewTransactionsStates by viewTransactionsViewModel.viewTransactionStates.collectAsStateWithLifecycle()
    val viewSavedItemsStates by viewSavedItemsViewModel.viewSavedItemsStates.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(
        initialPage = defaultTabIndex,
        pageCount = {2} // or however many pages you have
    )

    Log.d("RecordsPage", "pagerStateInitial ${pagerState.currentPage}")

    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            if(viewTransactionsStates.isSelectionMode){
                AppTopBar(
                    title = viewTransactionsStates.selectedTransactions.size.toString(),
                    showMenu = true,
                    showBackButton = true,
                    onBackClick = {
                        viewTransactionsViewModel.onEvent(
                            ViewTransactionsEvents.ExitSelectionMode
                        )
                    },
                    menuItems = if(viewTransactionsStates.selectedTransactions.size == 1){
                        listOf<MenuItems>(
                            MenuItems(
                                text = "Info",
                                onClick = {
//                                    navController.navigate(Screens.SingleTransactionScreen.routes)
                                    navController.navigate("${Screens.SingleTransactionScreen.routes}/${viewTransactionsStates.selectedTransactions.firstOrNull()}")

                                }
                            ),
                            MenuItems(
                                text = "Delete",
                                onClick = {
//                                    viewTransactionsViewModel.onEvent(
//                                        ViewTransactionsEvents.DeleteSelectedTransactions
//                                    )
                                    viewTransactionsViewModel.onEvent(
                                        ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewTransactionsViewModel.onEvent(ViewTransactionsEvents.SelectAllTransactions)
                                }
                            )
                        )
                    }
                    else{
                        listOf<MenuItems>(
                            MenuItems(
                                text = "Delete All",
                                onClick = {
//                                    viewTransactionsViewModel.onEvent(
//                                        ViewTransactionsEvents.DeleteSelectedTransactions
//                                    )
                                    viewTransactionsViewModel.onEvent(
                                        ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewTransactionsViewModel.onEvent(ViewTransactionsEvents.SelectAllTransactions)
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
                        viewSavedItemsViewModel.onEvent(
                            ViewSavedItemsEvents.ExitSelectionMode
                        )
                    },
                    menuItems = if(viewSavedItemsStates.selectedSavedItems.size == 1){
                        listOf<MenuItems>(
                            MenuItems(
                                text = "Info",
                                onClick = {
                                    navController.navigate("${Screens.SingleSavedItemScreen.routes}/${viewSavedItemsStates.selectedSavedItems.firstOrNull()}")
                                }
                            ),
                            MenuItems(
                                text = "Delete",
                                onClick = {
//                                    viewSavedItemsViewModel.onEvent(
//                                        ViewSavedItemsEvents.DeleteSelectedSavedItems
//                                    )
                                    viewSavedItemsViewModel.onEvent(
                                        ViewSavedItemsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Edit",
                                onClick = {

                                    viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = true))
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.SelectAllItems)

                                }
                            )
                        )
                    }
                    else{
                        listOf<MenuItems>(
                            MenuItems(
                                text = "Delete All",
                                onClick = {
//                                    viewSavedItemsViewModel.onEvent(
//                                        ViewSavedItemsEvents.DeleteSelectedSavedItems
//                                    )
                                    viewSavedItemsViewModel.onEvent(
                                        ViewSavedItemsEvents.ChangeCustomDateAlertBox(true)
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Select All",
                                onClick = {
                                    viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.SelectAllItems)
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

        },
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }

    ) { padding ->

        // Column now takes full height and includes padding for content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LaunchedEffect(pagerState.currentPage) {
                if (pagerState.currentPage == 0 && viewSavedItemsStates.isSelectionMode) {
                    viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ExitSelectionMode)
                }
                if (pagerState.currentPage == 1 && viewTransactionsStates.isSelectionMode) {
                    viewTransactionsViewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
                }
            }


            // TabRow should now be visible
            TabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                            if (viewSavedItemsStates.isSelectionMode) {
                                viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ExitSelectionMode)
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
                                viewTransactionsViewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
                            }
                        }
                    },
                    text = { Text("Saved Items") }
                )
            }

                // Uncomment this to enable HorizontalPager
                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> ViewTransactionsPage(viewModel = viewTransactionsViewModel, navController = navController) // Transactions Screen
                        1 -> ViewSavedItemsPage(viewModel = viewSavedItemsViewModel,navController = navController)
                    }
                }



        }

    }
}
