package com.example.financetracker.presentation.features.view_records_feature.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.domain.model.Currency
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.navigation.core.Screens
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.view_records_feature.events.ViewSavedItemsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewSavedItemsStates
import com.example.financetracker.presentation.features.view_records_feature.viewmodels.ViewSavedItemsViewModel
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.Locale

@Composable
fun SingleSavedItemRoot(
    navController: NavController,
    viewSavedItemsViewModel: ViewSavedItemsViewModel = hiltViewModel(),
    savedItemId: Int
){
    val states by viewSavedItemsViewModel.viewSavedItemsStates.collectAsStateWithLifecycle()
    val singleItemState by viewSavedItemsViewModel.savedItemState.collectAsStateWithLifecycle()
    val savedItemsValidationEvent = viewSavedItemsViewModel.savedItemsValidationEvents

    SingleSavedItemScreen(
        navController = navController,
        states = states,
        singleItemState = singleItemState,
        onEvent = viewSavedItemsViewModel::onEvent,
        savedItemsValidationEvent = viewSavedItemsViewModel.savedItemsValidationEvents,
        savedItemId = savedItemId
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleSavedItemScreen(
    navController: NavController,
    states: ViewSavedItemsStates,
    singleItemState: SavedItems?,
    onEvent: (ViewSavedItemsEvents) -> Unit,
    savedItemId: Int,
    savedItemsValidationEvent: Flow<AddTransactionViewModel.AddTransactionValidationEvent>
){
    val context = LocalContext.current

    LaunchedEffect(savedItemId) {
        onEvent(ViewSavedItemsEvents.GetSingleSavedItem(savedItemId))
    }

    LaunchedEffect(key1 = context) {
        savedItemsValidationEvent.collect { event ->
            when (event) {
                is AddTransactionViewModel.AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Item Successfully Updated", Toast.LENGTH_LONG).show()
                    onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = false))
                    onEvent(ViewSavedItemsEvents.RefreshUpdatedItem)
                }
                is AddTransactionViewModel.AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Log.d("ViewSavedItemsViewModelS","transaction Single $singleItemState")

    if(singleItemState != null){


        val itemName = singleItemState.itemName
        val itemCurrencyCode = singleItemState.itemCurrency.entries.firstOrNull()?.key
        val itemCurrencyName = singleItemState.itemCurrency.entries.firstOrNull()?.value?.name
        val itemCurrencySymbol = singleItemState.itemCurrency.entries.firstOrNull()?.value?.symbol ?: "$"
        val itemPrice = singleItemState.itemPrice
        val itemDescription = singleItemState.itemDescription
        val itemShopName = singleItemState.itemShopName

        Scaffold(

            topBar = {
                AppTopBar(
                    title = "Item Details",
                    showBackButton = true,
                    showMenu = false,
                    onBackClick = {
                        navController.popBackStack()
                        if(states.isSelectionMode){
                            onEvent(ViewSavedItemsEvents.ExitSelectionMode)
                        }
                    },
                    customActions = {
                        IconButton(
                            onClick = {
                                onEvent(
                                    ViewSavedItemsEvents.ChangeCustomDateAlertBox(true)
                                )
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                )
            }

        ) {paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if(states.customDeleteAlertBoxState){

                    DeleteConfirmationDialog(
                        onDismiss = {
                            onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
                        },
                        onConfirm = {
                            onEvent(ViewSavedItemsEvents.DeleteSelectedSavedItems)
                            onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
                            navController.navigate(Screens.ViewRecordsScreen(tabIndex = 1))

                        }
                    )

                }

                OutlinedTextField(
                    value = itemName,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = "$itemCurrencyName($itemCurrencyCode)",
                    onValueChange = {  },
                    readOnly = true,
                    label = { Text("Item Currency") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )


                OutlinedTextField(
                    value = String.format(Locale.US, "%.2f", itemPrice),
                    onValueChange = {  },
                    readOnly = true,
                    label = { Text("Item Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Text(
                            text = itemCurrencySymbol,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )

                OutlinedTextField(
                    value = if(itemDescription.isNullOrEmpty()) "No Description" else itemDescription,
                    onValueChange = {  },
                    readOnly = true,
                    label = { Text("Item Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = if(itemShopName.isNullOrEmpty()) "No Shop Name" else itemShopName,
                    onValueChange = {  },
                    readOnly = true,
                    label = { Text("Shop Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(true))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit")
                }
            }

            if(states.updateBottomSheetState){
                UpdateItemDetailsBottomSheet(
                    sheetState = rememberModalBottomSheetState(),
                    states = states,
                    onDismiss = {
                        onEvent(
                            ViewSavedItemsEvents.ChangeUpdateBottomSheetState(
                                state = false
                            )
                        )
                    },
                    onSaveClick = {
                        onEvent(ViewSavedItemsEvents.SaveItem)
                    },
                    onEvent = onEvent
                )
            }
        }
    }
    else{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "With Valid Saved Item"
)
@Composable
fun SingleSavedItemScreenPreview(){
    FinanceTrackerTheme {
        val savedItem = SavedItems(
            itemId = 1,
            itemName = "Wireless Headphones",
            itemCurrency = mapOf(
                "USD" to Currency(name = "US Dollar", symbol = "$"),
                "INR" to Currency(name = "Indian Rupee", symbol = "₹")
            ),
            itemPrice = 99.99,
            itemDescription = "Noise cancelling Bluetooth headphones",
            itemShopName = "Amazon",
            userUID = "user_123",
            cloudSync = true
        )

        SingleSavedItemScreen(
            navController = rememberNavController(),
            states = ViewSavedItemsStates(),
            singleItemState = savedItem,
            onEvent = {

            },
            savedItemId = 1,
            savedItemsValidationEvent = emptyFlow()
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "With Null"
)
@Composable
fun SingleSavedItemScreenPreview2(){
    FinanceTrackerTheme {
        SingleSavedItemScreen(
            navController = rememberNavController(),
            states = ViewSavedItemsStates(),
            singleItemState = null,
            onEvent = {

            },
            savedItemId = 1,
            savedItemsValidationEvent = emptyFlow()
        )
    }
}