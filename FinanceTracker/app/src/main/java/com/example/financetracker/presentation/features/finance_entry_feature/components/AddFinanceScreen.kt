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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.presentation.features.finance_entry_feature.events.AddSavedItemsEvents
import com.example.financetracker.presentation.features.finance_entry_feature.events.AddTransactionEvents
import com.example.financetracker.presentation.features.finance_entry_feature.states.AddSavedItemsStates
import com.example.financetracker.presentation.features.finance_entry_feature.states.AddTransactionStates
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddSavedItemViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel.AddTransactionValidationEvent
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun AddFinanceRoot(
    navController: NavController,
    addTransactionViewModel: AddTransactionViewModel = hiltViewModel(),
    addSavedItemViewModel: AddSavedItemViewModel = hiltViewModel()
){
    val addSavedItemsStates by addSavedItemViewModel.savedItemsState.collectAsStateWithLifecycle()
    val addTransactionsStates by addTransactionViewModel.addTransactionStates.collectAsStateWithLifecycle()
    val addTransactionsSelectedItem by addTransactionViewModel.selectedItem.collectAsStateWithLifecycle()

    AddFinanceScreen(
        navController = navController,
        addTransactionsStates = addTransactionsStates,
        addTransactionsSelectedItem = addTransactionsSelectedItem,
        savedItemsValidationEvent = addSavedItemViewModel.savedItemsValidationEvents,
        onAddTransactionsEvent = addTransactionViewModel::onEvent,
        onAddSavedItemsEvent = addSavedItemViewModel::onEvent,
        addTransactionValidationEvents = addTransactionViewModel.addTransactionsValidationEvents,
        addSavedItemsStates = addSavedItemsStates
    )
}

@Composable
fun AddFinanceScreen(
    navController: NavController,
    addTransactionsStates: AddTransactionStates,
    addTransactionsSelectedItem: SavedItems?,
    savedItemsValidationEvent: Flow<AddTransactionValidationEvent>,
    addTransactionValidationEvents: Flow<AddTransactionValidationEvent>,
    addSavedItemsStates: AddSavedItemsStates,
    onAddTransactionsEvent : (AddTransactionEvents) -> Unit,
    onAddSavedItemsEvent: (AddSavedItemsEvents) -> Unit
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
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
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

                 HorizontalPager(state = pagerState) { page ->
                     when (page) {
                         0 -> AddTransactionScreen(
                             navController = navController,
                             states = addTransactionsStates,
                             selectedItem = addTransactionsSelectedItem,
                             addTransactionValidationEvents = addTransactionValidationEvents,
                             onEvent = onAddTransactionsEvent
                         )
                         1 -> AddSavedItemsScreen(
                             navController = navController,
                             savedItemsValidationEvent = savedItemsValidationEvent,
                             states = addSavedItemsStates,
                             onEvent = onAddSavedItemsEvent
                         )
                     }
                 }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddFinanceScreenPreview() {
    FinanceTrackerTheme{
        AddFinanceScreen(
            navController = rememberNavController(),
            addTransactionsStates = AddTransactionStates(),
            addTransactionsSelectedItem = null,
            savedItemsValidationEvent = emptyFlow(),
            addTransactionValidationEvents = emptyFlow(),
            addSavedItemsStates = AddSavedItemsStates(),
            onAddTransactionsEvent = {

            },
            onAddSavedItemsEvent = {

            }
        )
    }
}