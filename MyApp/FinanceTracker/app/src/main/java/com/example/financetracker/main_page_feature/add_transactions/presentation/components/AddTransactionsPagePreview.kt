package com.example.financetracker.main_page_feature.add_transactions.presentation.components



import BottomNavigationBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.core.presentation.components.AppTopBar
import com.example.financetracker.main_page_feature.add_transactions.expense.presentation.AddExpenseTransactionsPage
import com.example.financetracker.main_page_feature.add_transactions.income.presentation.AddIncomeTransactionsPage
import com.example.financetracker.main_page_feature.add_transactions.saveItems.presentation.AddSaveItemsTransactionsPage
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TransactionsTabScreen() {
    val pagerState = rememberPagerState(pageCount = { 3 }) // 3 tabs
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Transactions",
                showMenu = true,
                showBackButton = false,
                onBackClick = {},
                menuItems = emptyList(),
                modifier = Modifier.height(80.dp)
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { padding ->

        // Column now takes full height and includes padding for content
        Column(modifier = Modifier
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
                    text = { Text("Add Items") }
                )
            }

//             Uncomment this to enable HorizontalPager
//             HorizontalPager(state = pagerState) { page ->
//                 when (page) {
//                     0 -> AddExpenseTransactionsPage() // Transactions Screen
//                     1 -> AddIncomeTransactionsPage() // Recurring Transactions
//                     2 -> AddSaveItemsTransactionsPage() // Analytics & Reports
//                 }
//             }
        }
    }
}