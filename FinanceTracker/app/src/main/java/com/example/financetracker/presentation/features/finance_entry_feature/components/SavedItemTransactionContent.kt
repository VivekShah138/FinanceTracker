package com.example.financetracker.presentation.features.finance_entry_feature.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.presentation.features.finance_entry_feature.events.AddTransactionEvents
import com.example.financetracker.presentation.features.finance_entry_feature.states.AddTransactionStates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedItemTransactionContent(
    onEvent : (AddTransactionEvents) -> Unit,
    states: AddTransactionStates,
    focusRequester: FocusRequester,
    selectedItem: SavedItems?
){


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // Search Bar
        OutlinedTextField(
            value = states.transactionName,
            onValueChange = {
                onEvent(AddTransactionEvents.ChangeTransactionName(it))
                onEvent(
                    AddTransactionEvents.FilterSavedItemList(
                        list = states.transactionSearchList,
                        newWord = it
                    )
                )
            },
            singleLine = true,
            label = { Text("Search for items") },
            leadingIcon = {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back Arrow",
                    modifier = Modifier.clickable {
                        onEvent(
                            AddTransactionEvents.ChangeSavedItemSearchState(false)
                        )
                        onEvent(
                            AddTransactionEvents.ChangeTransactionName("")
                        )
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        LaunchedEffect(Unit){
            focusRequester.requestFocus()
        }

        // Display filtered items in a list
        if (states.transactionSearchFilteredList.isNotEmpty()) {

            LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 800.dp)) {
                items(states.transactionSearchFilteredList) { item ->

                    SavedItemsCard(
                        item = item,
                        onClick = {

                            onEvent(
                                AddTransactionEvents.ChangeQuantity(true)
                            )

                            onEvent(
                                AddTransactionEvents.ChangeSelectedItem(item)
                            )

                        },
                        onLongClick = {},
                        isSelected = false,
                        isSelectionMode = false
                    )
                }
            }
        }


        if(states.quantityBottomSheetState){
            QuantityBottomSheet(
                onDismiss = {
                    onEvent(
                        AddTransactionEvents.ChangeQuantity(false)
                    )
                },
                onConfirm = { quantity ->


                    val currency = selectedItem?.itemCurrency ?: emptyMap()
                    val currencyName = currency.entries.firstOrNull()?.value?.name ?: "N/A"
                    val currencySymbol = currency.entries.firstOrNull()?.value?.symbol ?: "N/A"
                    val currencyCode = currency.entries.firstOrNull()?.key ?: "N/A"
                    val itemDescription = "$quantity * ${selectedItem?.itemPrice} $currencySymbol \n" + selectedItem?.itemDescription

                    // Name
                    onEvent(
                        AddTransactionEvents.ChangeTransactionName(selectedItem?.itemName ?: "Unknown")
                    )

                    // Currency
                    onEvent(
                        AddTransactionEvents.ChangeTransactionCurrency(
                            currencyName = currencyName,
                            currencySymbol = currencySymbol,
                            currencyCode = currencyCode,
                            currencyExpanded = false
                        )
                    )

                    // Description
                    onEvent(
                        AddTransactionEvents.ChangeTransactionDescription(itemDescription)
                    )

                    // Price
                    onEvent(
                        AddTransactionEvents.CalculateFinalPrice(
                            quantity = quantity,
                            price = selectedItem?.itemPrice ?: 0.0
                        )
                    )

                    // To Normal State
                    onEvent(
                        AddTransactionEvents.ChangeSavedItemSearchState(false)
                    )

                    onEvent(
                        AddTransactionEvents.ChangeQuantity(false)
                    )

                },
                sheetState = rememberModalBottomSheetState()
            )
        }
    }
}