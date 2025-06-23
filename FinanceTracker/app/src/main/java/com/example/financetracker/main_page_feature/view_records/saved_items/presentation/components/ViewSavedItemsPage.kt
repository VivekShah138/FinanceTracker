package com.example.financetracker.main_page_feature.view_records.saved_items.presentation.components

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionEvents
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components.SavedItemsCard
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsEvents
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsViewModel
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.components.DeleteConfirmationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSavedItemsPage(
    viewModel: ViewSavedItemsViewModel,
    navController: NavController
) {

    val states by viewModel.viewSavedItemsStates.collectAsStateWithLifecycle()
    val savedItemsValidationEvent = viewModel.savedItemsValidationEvents
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    BackHandler(enabled = states.isSelectionMode) {
        viewModel.onEvent(ViewSavedItemsEvents.ExitSelectionMode)
    }


    LaunchedEffect(key1 = context) {
        savedItemsValidationEvent.collect { event ->
            when (event) {
                is AddTransactionViewModel.AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Item Successfully Added", Toast.LENGTH_LONG).show()
                    viewModel.onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = false))
                    viewModel.onEvent(ViewSavedItemsEvents.ExitSelectionMode)
                }
                is AddTransactionViewModel.AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    if(states.customDeleteAlertBoxState){

        DeleteConfirmationDialog(
            onDismiss = {
                viewModel.onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
            },
            onConfirm = {
                viewModel.onEvent(ViewSavedItemsEvents.DeleteSelectedSavedItems)
                viewModel.onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
            },
            showDialog = states.customDeleteAlertBoxState
        )

    }


    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        SearchBar(
            states = states,
            viewModel = viewModel,
            focusRequester = focusRequester,
            focusManager = focusManager,
            isFocused = isFocused,
            interactionSource = interactionSource
        )


        if(!isFocused){
            LazyColumn {
                items(states.savedItemsList){ savedItems ->

                    val isSelected = states.selectedSavedItems.contains(savedItems.itemId)

                    SavedItemsCard(
                        item = savedItems,
                        onClick = {
                            if (states.isSelectionMode) {
                                viewModel.onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                            } else {
                                navController.navigate("${Screens.SingleSavedItemScreen.routes}/${savedItems.itemId}")
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
        else{
            LazyColumn {
                items(states.savedItemsFilteredList){ savedItems ->

                    val isSelected = states.selectedSavedItems.contains(savedItems.itemId)

                    SavedItemsCard(
                        item = savedItems,
                        onClick = {
                            if (states.isSelectionMode) {
                                viewModel.onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                            } else {
                                navController.navigate("${Screens.SingleSavedItemScreen.routes}/${savedItems.itemId}")
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

        if(states.updateBottomSheetState){
            UpdateItemDetailsBottomSheet(
                sheetState = rememberModalBottomSheetState(),
                viewModel = viewModel,
                states = states,
                onDismiss = {
                    viewModel.onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = false))
                },
                onSaveClick = {
                    viewModel.onEvent(ViewSavedItemsEvents.SaveItem)
                }
            )
        }
    }
}