package com.example.financetracker.presentation.features.finance_entry_feature.components

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.presentation.features.finance_entry_feature.events.AddTransactionEvents
import com.example.financetracker.presentation.features.finance_entry_feature.states.AddTransactionStates
import com.example.financetracker.presentation.features.finance_entry_feature.viewmodels.AddTransactionViewModel
import com.example.financetracker.presentation.features.setup_account_feature.components.AutoComplete
import com.example.financetracker.presentation.features.setup_account_feature.components.CustomSwitch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualTransactionContent(
    scrollState: ScrollState,
    onEvent : (AddTransactionEvents) -> Unit,
    states: AddTransactionStates,
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
            .imePadding()
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        // Saved Item Transaction
        CustomSwitch(
            text = "Use Saved Item?",
            isCheck = states.saveItemState,
            onCheckChange = {
                onEvent(AddTransactionEvents.ChangeSavedItemState(it))
                onEvent(AddTransactionEvents.ChangeTransactionName(""))
                onEvent(AddTransactionEvents.LoadSavedItemList)
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Transaction Type
        TransactionTypeSegmentedButton(
            selectedType = states.transactionType,
            onTypeSelected = { type->

                onEvent(
                    AddTransactionEvents.SelectTransactionType(type = type)
                )
                onEvent(
                    AddTransactionEvents.SelectCategory(
                        categoryName = "",
                        bottomSheetState = false,
                        alertBoxState = false
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Transaction Category
        TransactionCategoryButton(
            category = states.category,
            onClick = {
                onEvent(AddTransactionEvents.LoadCategory(type = states.transactionType))

                onEvent(
                    AddTransactionEvents.SelectCategory(
                        categoryName = states.category,
                        bottomSheetState = true,
                        alertBoxState = states.alertBoxState
                    )
                )
            }
        )

        // If view BottomSheet is true
        if(states.bottomSheetState){
            CustomBottomSheet(
                categories = states.categoryList,
                sheetState = rememberModalBottomSheetState(),
                onDismissRequest = {
                    onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                },
                onItemSelect = { category ->
                    onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = category.name,
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                },
                displayText = {category ->
                    category.name
                },
                onCustomAddClick = {
                    onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            alertBoxState = true,
                            bottomSheetState = true
                        )
                    )
                },
                selectedCategory = states.category,
                onClearSelection = {
                    onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = "",
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                }
            )
        }

        // if view alert box is true
        if(states.alertBoxState){
            CustomTextAlertBox(
                selectedCategory = states.category,
                onCategoryChange = {
                    onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = it,
                            bottomSheetState = true,
                            alertBoxState = true
                        )
                    )
                },
                onDismissRequest = {
                    onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = true,
                            alertBoxState = false
                        )
                    )
                },
                onSaveCategory = {
                    onEvent(
                        AddTransactionEvents.SaveCustomCategories
                    )
                    onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                },
                title = "Enter Custom Category",
                label = "Category Title"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Transaction Name
        if (states.saveItemState) {
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
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && !states.searchBarFocusedState) {
                            onEvent(AddTransactionEvents.ChangeSavedItemSearchState(true))
                        }
                    }
            )
        } else {
            OutlinedTextField(
                value = states.transactionName,
                onValueChange = {
                    onEvent(AddTransactionEvents.ChangeTransactionName(it))
                },
                label = { Text("Enter new item name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Transaction Description
        OutlinedTextField(
            value = states.transactionDescription,
            onValueChange = {
                onEvent(
                    AddTransactionEvents.ChangeTransactionDescription(it)
                )
            },
            label = { Text("Enter Item Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(10.dp))


        // Price
        OutlinedTextField(
            value = states.transactionPrice,
            onValueChange = {
                onEvent(
                    AddTransactionEvents.ChangeTransactionPrice(it)
                )
            },
            label = { Text("Price") },
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Text(
                    text = states.transactionCurrencySymbol,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))


        AutoComplete(
            categories = states.currencies,
            loadCountry = {
                onEvent(
                    AddTransactionEvents.LoadCurrenciesList
                )
            },
            onSearchValueChange = {
                onEvent(
                    AddTransactionEvents.ChangeSearchCurrency(it)
                )
            },
            expanded = states.transactionCurrencyExpanded,
            onExpandedChange = {
                onEvent(
                    AddTransactionEvents.ChangeTransactionCurrency(
                        currencyName = states.transactionCurrencyName,
                        currencyCode = states.transactionCurrencyCode,
                        currencySymbol = states.transactionCurrencySymbol,
                        currencyExpanded = it
                    )
                )
                if (it) {
                    onEvent(AddTransactionEvents.LoadCurrenciesList)
                }
            },
            onItemSelect = {
                val firstCurrency = it.currencies?.entries?.firstOrNull()



                val currencyName = firstCurrency?.value?.name ?: "N/A"
                val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
                val currencyCode = firstCurrency?.key ?: "N/A"

                onEvent(
                    AddTransactionEvents.ChangeTransactionCurrency(
                        currencyName = currencyName,
                        currencyCode = currencyCode,
                        currencySymbol = currencySymbol,
                        currencyExpanded = false
                    )
                )

                onEvent(
                    AddTransactionEvents.ShowConversion(
                        showConversion = true,
                        showExchangeRate = false
                    )
                )
            },
            category = if(states.transactionCurrencyExpanded) states.searchCurrency else "${states.transactionCurrencyName} (${states.transactionCurrencyCode})",
            label = "Base Currency",
            displayText = {
                it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
            }
        )



        Spacer(modifier = Modifier.height(10.dp))

        if(states.showConversion){
            CurrencyConversionSection(
                exchangeRate = states.transactionExchangeRate,
                convertedAmount = states.convertedPrice,
                onConvertClick = {
                    onEvent(
                        AddTransactionEvents.SetConvertedTransactionPrice(price = states.transactionPrice,rate = states.transactionExchangeRate)
                    )
                    if(states.transactionPrice.isNotEmpty() || states.transactionPrice.isNotBlank()){
                        onEvent(
                            AddTransactionEvents.ShowConversion(
                                showConversion = true,
                                showExchangeRate = true
                            )
                        )
                    }
                },
                baseCurrencySymbol = states.baseCurrencySymbol,
                transactionCurrencySymbol = states.transactionCurrencySymbol,
                isConverted = states.showExchangeRate
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onEvent(
                    AddTransactionEvents.AddTransactionTransaction
                )
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Add")
        }

    }
}