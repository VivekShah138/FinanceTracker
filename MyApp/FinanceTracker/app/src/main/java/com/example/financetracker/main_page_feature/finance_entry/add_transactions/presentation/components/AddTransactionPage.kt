package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components

import QuantityBottomSheet
import TransactionTypeSegmentedButton
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionEvents
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionStates
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel
import com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.CustomBottomSheet
import com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.CustomTextAlertBox
import com.example.financetracker.setup_account.presentation.components.CustomSwitch
import com.example.financetracker.setup_account.presentation.components.SimpleDropdownMenu



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionPage(
    navController: NavController,
    viewModel: AddTransactionViewModel
){
    val addTransactionValidationEvents = viewModel.addTransactionsValidationEvents
    val states by viewModel.addTransactionStates.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }



    LaunchedEffect(key1 = context) {
        addTransactionValidationEvents.collect { event ->
            when (event) {
                is AddTransactionViewModel.AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage,Toast.LENGTH_SHORT).show()
                }
                AddTransactionViewModel.AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Transaction Successfully Added",Toast.LENGTH_LONG).show()
                    navController.navigate(route = Screens.HomePageScreen.routes)
                }
            }
        }
    }




    Box(
        modifier = Modifier.fillMaxSize()
    ){
        if(states.isFocusedSearchBar){

            WithSearchableMode(
                viewModel = viewModel,
                states = states,
                focusRequester = focusRequester
            )
        }
        else{
            WithoutSearchableMode(
                scrollState = scrollState,
                viewModel = viewModel,
                states = states,
                focusRequester = focusRequester
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithSearchableMode(
    viewModel: AddTransactionViewModel,
    states: AddTransactionStates,
    focusRequester: FocusRequester
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
                viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(it))
                viewModel.onEvent(
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
                        viewModel.onEvent(
                            AddTransactionEvents.ChangeSavedItemSearchState(false)
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
            Log.d("AddTransactionPage", "FilteredListNot Empty")
            Log.d("AddTransactionPage", "FilteredList ${states.transactionSearchFilteredList}")

            LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 800.dp)) {
                items(states.transactionSearchFilteredList) { item ->
                    Log.d("AddTransactionPage", "Rendering item: ${item.itemName}")

                    ItemCard(
                        itemName = item.itemName,
                        itemId = item.itemId.toString(),
                        itemDescription = item.itemDescription ?: "N/A",
                        price = item.itemPrice.toString(),
                        shopName = item.itemShopName ?: "N/A",
                        onClick = {

                            viewModel.onEvent(
                                AddTransactionEvents.ChangeQuantity(true)
                            )

                        }
                    )

                    if(states.itemQuantityState){
                        QuantityBottomSheet(
                            onDismiss = {
                                viewModel.onEvent(
                                    AddTransactionEvents.ChangeQuantity(false)
                                )
                            },
                            onConfirm = { quantity ->


                                val currency = item.itemCurrency
                                val currencyName = currency.entries.firstOrNull()?.value?.name ?: "N/A"
                                val currencySymbol = currency.entries.firstOrNull()?.value?.symbol ?: "N/A"
                                val currencyCode = currency.entries.firstOrNull()?.key ?: "N/A"
                                val itemDescription = "$quantity * ${item.itemPrice} $currencySymbol \n" + item.itemDescription

                                // Name
                                viewModel.onEvent(
                                    AddTransactionEvents.ChangeTransactionName(item.itemName)
                                )

                                // Currency
                                viewModel.onEvent(
                                    AddTransactionEvents.ChangeTransactionCurrency(
                                        currencyName = currencyName,
                                        currencySymbol = currencySymbol,
                                        currencyCode = currencyCode,
                                        currencyExpanded = false
                                    )
                                )

                                // Description
                                viewModel.onEvent(
                                    AddTransactionEvents.ChangeTransactionDescription(itemDescription)
                                )

                                // Price
                                viewModel.onEvent(
                                    AddTransactionEvents.CalculateFinalPrice(
                                        quantity = quantity,
                                        price = item.itemPrice ?: 0.0
                                    )
                                )

                                // To Normal State
                                viewModel.onEvent(
                                    AddTransactionEvents.ChangeSavedItemSearchState(false)
                                )

                                viewModel.onEvent(
                                    AddTransactionEvents.ChangeQuantity(false)
                                )

                            },
                            sheetState = rememberModalBottomSheetState()
                        )
                    }

                }
            }
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithoutSearchableMode(
    scrollState: ScrollState,
    viewModel: AddTransactionViewModel,
    states: AddTransactionStates,
    focusRequester: FocusRequester
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
            .imePadding()
    ) {

        Spacer(modifier = Modifier.height(5.dp))

        // Recurring Transaction
        CustomSwitch(
            text = "Recurring Transaction?",
            isCheck = states.isRecurring,
            onCheckChange = {
                viewModel.onEvent(
                    AddTransactionEvents.ChangeRecurringItemState(it)
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Saved Item Transaction
        CustomSwitch(
            text = "Saved Item?",
            isCheck = states.saveItemState,
            onCheckChange = {
                viewModel.onEvent(AddTransactionEvents.ChangeSavedItemState(it))
                viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(""))
                viewModel.onEvent(AddTransactionEvents.LoadSavedItemList)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Transaction Type
        TransactionTypeSegmentedButton(
            selectedType = states.transactionType,
            onTypeSelected = { type->

                viewModel.onEvent(
                    AddTransactionEvents.SelectTransactionType(type = type)
                )
                viewModel.onEvent(
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
                viewModel.onEvent(AddTransactionEvents.LoadCategory(type = states.transactionType))

                viewModel.onEvent(
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
                    viewModel.onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                },
                onItemSelect = { category ->
                    viewModel.onEvent(
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
                    viewModel.onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            alertBoxState = true,
                            bottomSheetState = true
                        )
                    )
                },
                selectedCategory = states.category,
                onClearSelection = {
                    viewModel.onEvent(
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
                    viewModel.onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = it,
                            bottomSheetState = true,
                            alertBoxState = true
                        )
                    )
                },
                onDismissRequest = {
                    viewModel.onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = true,
                            alertBoxState = false
                        )
                    )
                },
                onSaveCategory = {
                    viewModel.onEvent(
                        AddTransactionEvents.SaveCustomCategories
                    )
                    viewModel.onEvent(
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
                    viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(it))
                    Log.d("AddTransactionPage","List ${states.transactionSearchList}")
                    viewModel.onEvent(
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
                    .onFocusChanged {
                    viewModel.onEvent(AddTransactionEvents.ChangeSavedItemSearchState(it.hasFocus))
                }
            )
        } else {
            OutlinedTextField(
                value = states.transactionName,
                onValueChange = {
                    viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(it))
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
                viewModel.onEvent(
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
                viewModel.onEvent(
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

        // Transaction Currency
        SimpleDropdownMenu(
            label = "Transaction Currency",
            selectedText = "${states.transactionCurrencyName} (${states.transactionCurrencyCode})",
            expanded = states.transactionCurrencyExpanded,
            list = states.currencies,
            onExpandedChange = {
                viewModel.onEvent(
                    AddTransactionEvents.ChangeTransactionCurrency(
                        currencyName = states.transactionCurrencyName,
                        currencyCode = states.transactionCurrencyCode,
                        currencySymbol = states.transactionCurrencySymbol,
                        currencyExpanded = it
                    )
                )
                if (it) {
                    viewModel.onEvent(AddTransactionEvents.LoadCurrenciesList)
                }
            },
            onDismissRequest = {
                viewModel.onEvent(
                    AddTransactionEvents.ChangeTransactionCurrency(
                        currencyName = states.transactionCurrencyName,
                        currencyCode = states.transactionCurrencyCode,
                        currencySymbol = states.transactionCurrencySymbol,
                        currencyExpanded = !states.transactionCurrencyExpanded
                    )
                )

            },
            displayText = {
                it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
            },
            onItemSelect = {
                val firstCurrency = it.currencies?.entries?.firstOrNull()



                val currencyName = firstCurrency?.value?.name ?: "N/A"
                val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
                val currencyCode = firstCurrency?.key ?: "N/A"

                Log.d("ProfileSetUp","firstCurrency BaseCurrency $firstCurrency")
                Log.d("ProfileSetUp","currencyName BaseCurrency $currencyName")
                Log.d("ProfileSetUp","currencyCode BaseCurrency $currencySymbol")
                Log.d("ProfileSetUp","currencySymbol BaseCurrency $currencyCode")

                viewModel.onEvent(
                    AddTransactionEvents.ChangeTransactionCurrency(
                        currencyName = currencyName,
                        currencyCode = currencyCode,
                        currencySymbol = currencySymbol,
                        currencyExpanded = false
                    )
                )

                viewModel.onEvent(
                    AddTransactionEvents.ShowConversion(
                        showConversion = true,
                        showExchangeRate = false
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        if(states.showConversion){
            CurrencyConversionSection(
                exchangeRate = states.transactionExchangeRate,
                convertedAmount = states.convertedPrice,
                onConvertClick = {
                    viewModel.onEvent(
                        AddTransactionEvents.SetConvertedTransactionPrice(price = states.transactionPrice,rate = states.transactionExchangeRate)
                    )
                    viewModel.onEvent(
                        AddTransactionEvents.ShowConversion(
                            showConversion = true,
                            showExchangeRate = true
                        )
                    )
                },
                baseCurrencySymbol = states.baseCurrencySymbol,
                transactionCurrencySymbol = states.transactionCurrencySymbol,
                isConverted = states.showExchangeRate
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.onEvent(
                    AddTransactionEvents.AddTransactionTransaction
                )
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Add")
        }

    }
}