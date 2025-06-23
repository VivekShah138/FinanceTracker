package com.example.financetracker.main_page_feature.view_records.saved_items.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsEvents
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsViewModel
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.components.DeleteConfirmationDialog
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleSavedItemScreen(
    navController: NavController,
    viewSavedItemsViewModel: ViewSavedItemsViewModel,
    savedItemId: Int
){

    val states by viewSavedItemsViewModel.viewSavedItemsStates.collectAsStateWithLifecycle()
    val singleItemState by viewSavedItemsViewModel.savedItemState.collectAsStateWithLifecycle()
    val savedItemsValidationEvent = viewSavedItemsViewModel.savedItemsValidationEvents
    val context = LocalContext.current

    LaunchedEffect(savedItemId) {
        viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.GetSingleSavedItem(savedItemId))
    }

    LaunchedEffect(key1 = context) {
        savedItemsValidationEvent.collect { event ->
            when (event) {
                is AddTransactionViewModel.AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Item Successfully Updated", Toast.LENGTH_LONG).show()
                    viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = false))
                    viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.RefreshUpdatedItem)
                }
                is AddTransactionViewModel.AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val savedItem = singleItemState
    Log.d("ViewSavedItemsViewModelS","transaction Single ${savedItem}")

    if(savedItem!=null){


        val itemName = savedItem.itemName
        val itemCurrencyCode = savedItem.itemCurrency?.entries?.firstOrNull()?.key
        val itemCurrencyName = savedItem.itemCurrency?.entries?.firstOrNull()?.value?.name
        val itemCurrencySymbol = savedItem.itemCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"
        val itemPrice = savedItem.itemPrice
        val itemDescription = savedItem.itemDescription
        val itemShopName = savedItem.itemShopName

        Scaffold(

            topBar = {
                AppTopBar(
                    title = "Item Details",
                    showBackButton = true,
                    showMenu = false,
                    onBackClick = {
                        navController.popBackStack()
                        if(states.isSelectionMode){
                            viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ExitSelectionMode)
                        }
                    },
                    customActions = {
                        IconButton(
                            onClick = {
//                                viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.DeleteSelectedSavedItems)
////                                navController.navigate(Screens.ViewRecordsScreen.routes)
//                                navController.navigate("${Screens.ViewRecordsScreen.routes}/1")
                                viewSavedItemsViewModel.onEvent(
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
                            viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
                        },
                        onConfirm = {
                            viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.DeleteSelectedSavedItems)
//                                navController.navigate(Screens.ViewRecordsScreen.routes)
                            viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ChangeCustomDateAlertBox(state = false))
                            navController.navigate("${Screens.ViewRecordsScreen.routes}/1")

                        },
                        showDialog = states.customDeleteAlertBoxState
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
                        viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(true))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit")
                }
            }

            if(states.updateBottomSheetState){
                UpdateItemDetailsBottomSheet(
                    sheetState = rememberModalBottomSheetState(),
                    viewModel = viewSavedItemsViewModel,
                    states = states,
                    onDismiss = {
                        viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.ChangeUpdateBottomSheetState(state = false))
                    },
                    onSaveClick = {
                        viewSavedItemsViewModel.onEvent(ViewSavedItemsEvents.SaveItem)
                    }
                )
            }

        }

    }
    else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}