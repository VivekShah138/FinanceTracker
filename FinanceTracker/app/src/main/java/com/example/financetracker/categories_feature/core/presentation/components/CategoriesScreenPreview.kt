package com.example.financetracker.categories_feature.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.PrimaryKey
import com.example.financetracker.categories_feature.expense.presentation.components.ExpenseCategoriesPage
import com.example.financetracker.categories_feature.income.presentation.components.IncomeCategoriesPage
import com.example.financetracker.core.core_presentation.components.AppTopBar
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CategoriesScreenPreview(

) {

    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Categories",
                showBackButton = true,
                showMenu = false,
                onBackClick = {

                }
            )
        }

    ) { padding ->
        Column(
            Modifier
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
            }
            
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 30.dp)

            ) {
                Text("Add Categories")
            }

            // Uncomment this to enable HorizontalPager
//            HorizontalPager(state = pagerState) { page ->
//                when (page) {
//                    0 -> ExpenseCategoriesPage(viewModel = expenseCategoriesViewModel)
//                    1 -> IncomeCategoriesPage(viewModel = incomeCategoriesViewModel)
//                }
//            }

        }
    }
}