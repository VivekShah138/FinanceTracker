package com.example.financetracker.main_page_feature.add_transactions.presentation.components



import BottomNavigationBar
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
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.main_page_feature.add_transactions.expense.presentation.AddExpensePage
import com.example.financetracker.main_page_feature.add_transactions.expense.presentation.AddExpenseViewModel
import com.example.financetracker.main_page_feature.add_transactions.income.presentation.AddIncomeTransactionsPage
import com.example.financetracker.main_page_feature.add_transactions.saveItems.presentation.AddSaveItemsTransactionsPage
import kotlinx.coroutines.launch

@Composable
fun AddTransactionsPage(
    navController: NavController,
    viewModel: AddExpenseViewModel
){
    MaterialTheme {
        val pagerState = rememberPagerState(pageCount = { 3 }) // 3 tabs
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Add Transactions",
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
                        text = { Text("Income") }
                    )
                    Tab(
                        selected = pagerState.currentPage == 2,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(2)
                            }
                        },
                        text = { Text("Save Items") }
                    )
                }

                // Uncomment this to enable HorizontalPager
                 HorizontalPager(state = pagerState) { page ->
                     when (page) {
                         0 -> AddExpensePage(viewModel = viewModel) // Transactions Screen
                         1 -> AddIncomeTransactionsPage() // Recurring Transactions
                         2 -> AddSaveItemsTransactionsPage() // Analytics & Reports
                     }
                 }
            }
        }
    }
}