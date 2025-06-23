package com.example.financetracker.categories_feature.core.presentation.components

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.categories_feature.core.presentation.CoreCategoriesEvents
import com.example.financetracker.categories_feature.core.presentation.CoreCategoriesViewModel
import com.example.financetracker.categories_feature.expense.presentation.ExpenseCategoriesViewModel
import com.example.financetracker.categories_feature.expense.presentation.components.ExpenseCategoriesPage
import com.example.financetracker.categories_feature.income.presentation.components.IncomeCategoriesPage
import com.example.financetracker.categories_feature.income.presentation.IncomeCategoriesViewModel
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.CustomTextAlertBox
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(
    navController: NavController,
    expenseCategoriesViewModel: ExpenseCategoriesViewModel,
    incomeCategoriesViewModel: IncomeCategoriesViewModel,
    coreCategoriesViewModel: CoreCategoriesViewModel
){

    val states  by  coreCategoriesViewModel.coreCategoriesState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Categories",
                showBackButton = true,
                showMenu = false,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    ){padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LaunchedEffect(pagerState.currentPage) {
                coreCategoriesViewModel.onEvent(
                    CoreCategoriesEvents.SelectCategoryType(
                        if (pagerState.currentPage == 0) "Expense" else "Income"
                    )
                )
            }


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

            // Uncomment this to enable HorizontalPager
            HorizontalPager(state = pagerState,
                modifier = Modifier.weight(1f) ) { page ->
                when (page) {
                    0 -> ExpenseCategoriesPage(viewModel = expenseCategoriesViewModel)
                    1 -> IncomeCategoriesPage(viewModel = incomeCategoriesViewModel)
                }
            }

            Button(
                onClick = {
                    coreCategoriesViewModel.onEvent(
                        CoreCategoriesEvents.ChangeCategoryAlertBoxState(state = true)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 30.dp)

            ) {
                Text("Add Categories")
            }

            if(states.addCategoryAlertBoxState){
                CustomTextAlertBox(
                    selectedCategory = states.categoryName,
                    onCategoryChange = {
                        coreCategoriesViewModel.onEvent(
                            CoreCategoriesEvents.ChangeCategoryName(it)
                        )
                    },
                    onDismissRequest = {
                        coreCategoriesViewModel.onEvent(
                            CoreCategoriesEvents.ChangeCategoryAlertBoxState(state = false)
                        )
                    },
                    onSaveCategory = {
                        coreCategoriesViewModel.onEvent(
                            CoreCategoriesEvents.AddCategory
                        )
                        coreCategoriesViewModel.onEvent(
                            CoreCategoriesEvents.ChangeCategoryAlertBoxState(state = false)
                        )
                    },
                    title = "Enter Custom Category",
                    label = "Category Title"

                )
            }
        }
    }

}