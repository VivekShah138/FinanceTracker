package com.example.financetracker.main_page_feature.view_records.saved_items.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components.SavedItemsCard
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsEvents
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsViewModel
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents

@Composable
fun ViewSavedItemsPage(
    viewModel: ViewSavedItemsViewModel,
    navController: NavController
) {

    val states by viewModel.viewSavedItemsStates.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyColumn {
            items(states.savedItemsList){ savedItems ->

                val isSelected = states.selectedSavedItems.contains(savedItems.itemId)

                SavedItemsCard(
                    item = savedItems,
                    onClick = {
                        if (states.isSelectionMode) {
                            viewModel.onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                        } else {
                            // Normal click action
                        }
                    },
                    onLongClick = {
                        if (!states.isSelectionMode) {
                            viewModel.onEvent(ViewSavedItemsEvents.EnterSelectionMode)
                            viewModel.onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                        }
                    },
                    isSelectionMode = states.isSelectionMode,
                    isSelected = isSelected
                )
            }
        }
    }

}