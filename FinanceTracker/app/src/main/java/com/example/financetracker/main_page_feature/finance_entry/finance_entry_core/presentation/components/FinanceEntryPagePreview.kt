package com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components



import BottomNavigationBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.financetracker.core.core_presentation.components.AppTopBar
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TransactionsTabScreen() {
    val pagerState = rememberPagerState(pageCount = { 2 }) // 3 tabs
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Finance Entry",
                showMenu = true,
                showBackButton = false,
                onBackClick = {},
                menuItems = emptyList(),
                modifier = Modifier.height(80.dp)
            )
        },
//        bottomBar = {
//            BottomNavigationBar()
//        }
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
                            pagerState.animateScrollToPage(2)
                        }
                    },
                    text = { Text("Add Items") }
                )
            }
        }
    }
}