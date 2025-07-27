package com.example.financetracker.presentation.features.finance_entry_feature.components



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.SavedItemViewModel
import kotlinx.coroutines.launch

@Composable
fun FinanceEntryPage(
    navController: NavController,
    addTransactionViewModel: AddTransactionViewModel,
    savedItemViewModel: SavedItemViewModel
){
    MaterialTheme {
        val pagerState = rememberPagerState(pageCount = { 2 })
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Finance Entry",
                    showMenu = true,
                    showBackButton = false,
                    onBackClick = {},
                    menuItems = emptyList()
                )
            },
//            bottomBar = {
//                BottomNavigationBar(navController)
//            }
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
                        text = { Text("Expense") }
                    )
                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        },
                        text = { Text("Save Items") }
                    )
                }

                // Uncomment this to enable HorizontalPager
                 HorizontalPager(state = pagerState) { page ->
                     when (page) {
                         0 -> AddTransactionPage(viewModel = addTransactionViewModel, navController = navController) // Transactions Screen
                         1 -> SavedItemPage(viewModel = savedItemViewModel,navController = navController)
                     }
                 }
            }
        }
    }
}