package com.example.financetracker.main_page_feature.view_records.presentation.components

import BottomNavigationBar
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.MenuItems
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.home_page.presentation.HomePageEvents
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
    viewSavedItemsViewModel: ViewSavedItemsViewModel
) {

    val viewTransactionsStates by viewTransactionsViewModel.viewTransactionStates.collectAsStateWithLifecycle()
    val viewSavedItemsStates by viewSavedItemsViewModel.viewSavedItemsStates.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            if(viewTransactionsStates.isSelectionMode){
                AppTopBar(
                    title = "Selected Transactions",
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
                                onClick = {}
                            ),
                            MenuItems(
                                text = "Delete",
                                onClick = {
                                    viewTransactionsViewModel.onEvent(
                                        ViewTransactionsEvents.DeleteSelectedTransactions
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Edit",
                                onClick = {


                                }
                            )
                        )
                    }
                    else{
                        listOf<MenuItems>(
                            MenuItems(
                                text = "Delete All",
                                onClick = {
                                    viewTransactionsViewModel.onEvent(
                                        ViewTransactionsEvents.DeleteSelectedTransactions
                                    )
                                }
                            )
                        )
                    }
                )
            }
            else if(viewSavedItemsStates.isSelectionMode){
                AppTopBar(
                    title = "Selected Saved Items",
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
                                onClick = {}
                            ),
                            MenuItems(
                                text = "Delete",
                                onClick = {
                                    viewSavedItemsViewModel.onEvent(
                                        ViewSavedItemsEvents.DeleteSelectedSavedItems
                                    )
                                }
                            ),
                            MenuItems(
                                text = "Edit",
                                onClick = {

                                    viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = true))
                                }
                            )
                        )
                    }
                    else{
                        listOf<MenuItems>(
                            MenuItems(
                                text = "Delete All",
                                onClick = {
                                    viewSavedItemsViewModel.onEvent(
                                        ViewSavedItemsEvents.DeleteSelectedSavedItems
                                    )
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
        bottomBar = {
            BottomNavigationBar(navController)
        }

    ) { padding ->

        // Column now takes full height and includes padding for content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // TabRow should now be visible
            TabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    text = { Text("Transactions") }
                )
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
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
