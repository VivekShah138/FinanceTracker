package com.example.financetracker.presentation.features.category_feature.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.domain.model.Category
import com.example.financetracker.presentation.features.category_feature.events.CoreCategoriesEvents
import com.example.financetracker.presentation.features.category_feature.viewmodel.CoreCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.ExpenseCategoriesViewModel
import com.example.financetracker.presentation.features.category_feature.viewmodel.IncomeCategoriesViewModel
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.presentation.features.category_feature.events.SharedCategoriesEvents
import com.example.financetracker.presentation.features.category_feature.states.CategoriesStates
import com.example.financetracker.presentation.features.finance_entry_feature.components.CustomTextAlertBox
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.launch

@Composable
fun CategoriesRoot(
    navController: NavController,
    expenseCategoriesViewModel: ExpenseCategoriesViewModel = hiltViewModel(),
    incomeCategoriesViewModel: IncomeCategoriesViewModel = hiltViewModel(),
    coreCategoriesViewModel: CoreCategoriesViewModel = hiltViewModel()
){
    val states  by  coreCategoriesViewModel.coreCategoriesState.collectAsStateWithLifecycle()

    val expenseCategoriesStates by expenseCategoriesViewModel.expenseCategoriesState.collectAsStateWithLifecycle()
    val incomeCategoriesStates by incomeCategoriesViewModel.incomeCategoriesState.collectAsStateWithLifecycle()
    val incomeCategoryStates by incomeCategoriesViewModel.categoryState.collectAsStateWithLifecycle()
    val expenseCategoryStates by expenseCategoriesViewModel.categoryState.collectAsStateWithLifecycle()


    CategoriesScreen(
        navController = navController,
        states = states,
        onEvent = coreCategoriesViewModel::onEvent,
        onSharedIncomeEvent = incomeCategoriesViewModel::onEvent,
        onSharedExpenseEvent = expenseCategoriesViewModel::onEvent,
        expenseCategoriesStates = expenseCategoriesStates,
        incomeCategoriesStates = incomeCategoriesStates,
        expenseCategoryStates = expenseCategoryStates,
        incomeCategoryStates = incomeCategoryStates
    )
}

@Composable
fun CategoriesScreen(
    navController: NavController,
    states: CategoriesStates,
    onEvent: (CoreCategoriesEvents) -> Unit,
    onSharedIncomeEvent: (SharedCategoriesEvents) -> Unit,
    onSharedExpenseEvent: (SharedCategoriesEvents) -> Unit,
    expenseCategoriesStates: CategoriesStates,
    incomeCategoriesStates: CategoriesStates,
    expenseCategoryStates: Category?,
    incomeCategoryStates: Category?

){

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
                onEvent(
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

            HorizontalPager(state = pagerState,
                modifier = Modifier.weight(1f) ) { page ->
                when (page) {
                    0 -> ExpenseCategoriesScreen(
                        states = expenseCategoriesStates,
                        categoryStates = expenseCategoryStates,
                        onEvent = onSharedExpenseEvent
                    )
                    1 -> IncomeCategoriesScreen(
                        states = incomeCategoriesStates,
                        categoryStates = incomeCategoryStates,
                        onEvent = onSharedIncomeEvent
                    )
                }
            }

            Button(
                onClick = {
                    onEvent(
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
                        onEvent(
                            CoreCategoriesEvents.ChangeCategoryName(it)
                        )
                    },
                    onDismissRequest = {
                        onEvent(
                            CoreCategoriesEvents.ChangeCategoryAlertBoxState(state = false)
                        )
                    },
                    onSaveCategory = {
                        onEvent(
                            CoreCategoriesEvents.AddCategory
                        )
                        onEvent(
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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CategoriesScreenPreview() {
    FinanceTrackerTheme {
        CategoriesScreen(
            navController = rememberNavController(),
            states = CategoriesStates(),
            onEvent = {

            },
            onSharedIncomeEvent = {

            },
            onSharedExpenseEvent = {

            },
            expenseCategoriesStates = CategoriesStates(),
            incomeCategoriesStates = CategoriesStates(),
            expenseCategoryStates = null,
            incomeCategoryStates = null
        )
    }
}
