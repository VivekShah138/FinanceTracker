package com.example.financetracker.presentation.features.view_records_feature.components

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.finance_entry_feature.components.SavedItemsCard
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel.AddTransactionValidationEvent
import com.example.financetracker.presentation.features.view_records_feature.events.ViewSavedItemsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewSavedItemsStates
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewSavedItemsViewModel
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSavedItemsPage(
    states: ViewSavedItemsStates,
    savedItemsValidationEvent: Flow<AddTransactionValidationEvent>,
    onEvent: (ViewSavedItemsEvents) -> Unit,
    navController: NavController
) {

    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    BackHandler(enabled = states.isSelectionMode) {
        onEvent(ViewSavedItemsEvents.ExitSelectionMode)
    }


    LaunchedEffect(key1 = context) {
        savedItemsValidationEvent.collect { event ->
            when (event) {
                is AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Item Successfully Added", Toast.LENGTH_LONG).show()
                    onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = false))
                    onEvent(ViewSavedItemsEvents.ExitSelectionMode)
                }
                is AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    if(states.customDeleteAlertBoxState){

        DeleteConfirmationDialog(
            onDismiss = {
                onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
            },
            onConfirm = {
                onEvent(ViewSavedItemsEvents.DeleteSelectedSavedItems)
                onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
            }
        )

    }


    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        SearchBar(
            states = states,
            onEvent = onEvent,
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
                                onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                            } else {
                                val savedItemId = savedItems.itemId
                                if (savedItemId != null) {
                                    navController.navigate(Screens.SingleSavedItemScreen(savedItemId))
                                }
                            }
                        },
                        onLongClick = {
                            if (!states.isSelectionMode) {
                                onEvent(ViewSavedItemsEvents.EnterSelectionMode)
                                onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                            }
                        },
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
                                onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                            } else {
                                val savedItemId = savedItems.itemId
                                if (savedItemId != null) {
                                    navController.navigate(Screens.SingleSavedItemScreen(savedItemId))
                                }
                            }
                        },
                        onLongClick = {
                            if (!states.isSelectionMode) {
                                onEvent(ViewSavedItemsEvents.EnterSelectionMode)
                                onEvent(ViewSavedItemsEvents.ToggleSavedItemSelection(savedItems.itemId!!))
                            }
                        },
                        isSelected = isSelected
                    )
                }
            }
        }

        if(states.updateBottomSheetState){
            UpdateItemDetailsBottomSheet(
                sheetState = rememberModalBottomSheetState(),
                onEvent = onEvent,
                states = states,
                onDismiss = {
                    onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = false))
                },
                onSaveClick = {
                    onEvent(ViewSavedItemsEvents.SaveItem)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ViewSavedItemsPagePreview(){
    FinanceTrackerTheme { 
        ViewSavedItemsPage(
            navController = rememberNavController(),
            onEvent = {

            },
            states = ViewSavedItemsStates(),
            savedItemsValidationEvent = emptyFlow(),
        )
    }
}