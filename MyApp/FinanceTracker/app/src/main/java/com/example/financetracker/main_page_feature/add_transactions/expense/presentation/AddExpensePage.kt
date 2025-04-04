package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.main_page_feature.add_transactions.presentation.components.CustomBottomSheet
import com.example.financetracker.main_page_feature.add_transactions.presentation.components.CustomTextAlertBox
import com.example.financetracker.setup_account.presentation.ProfileSetUpEvents
import com.example.financetracker.setup_account.presentation.components.CustomSwitch
import com.example.financetracker.setup_account.presentation.components.SimpleDropdownMenu


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpensePage(
    viewModel: AddExpenseViewModel
){
    val addTransactionValidationEvents = viewModel.addTransactionsValidationEvents
    val states by viewModel.addExpenseStates.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        addTransactionValidationEvents.collect { event ->
            when (event) {
                is AddExpenseViewModel.AddTransactionEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage,Toast.LENGTH_SHORT).show()
                }
                AddExpenseViewModel.AddTransactionEvent.Success -> {
                    Toast.makeText(context,"Profile Successfully Update",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState)
    ) {

        CustomSwitch(
            text = "Saved Item?",
            isCheck = states.saveItemState,
            onCheckChange = {
                viewModel.onEvent(AddExpenseEvents.ChangeSavedItemState(it))
                viewModel.onEvent(AddExpenseEvents.ChangeTransactionName(""))
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (states.saveItemState) {
            OutlinedTextField(
                value = states.transactionName,
                onValueChange = {
                    viewModel.onEvent(AddExpenseEvents.ChangeTransactionName(it))
                },
                label = { Text("Search for items") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            OutlinedTextField(
                value = states.transactionName,
                onValueChange = {
                    viewModel.onEvent(AddExpenseEvents.ChangeTransactionName(it))
                },
                label = { Text("Enter new item name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Dropdown button to open Bottom Sheet
        OutlinedButton(
            onClick = {
                viewModel.onEvent(
                    AddExpenseEvents.SelectCategory(
                        categoryName = states.category,
                        bottomSheetState = true,
                        alertBoxState = states.alertBoxState
                    )
                )
                viewModel.onEvent(AddExpenseEvents.LoadCategory(type = "expense"))
                viewModel.onEvent(AddExpenseEvents.LoadCurrencyRates)
            },
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    states.category.ifEmpty {
                        "Select a Category"
                    },
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }

        if(states.bottomSheetState){
            CustomBottomSheet(
                categories = states.categoryList,
                sheetState = rememberModalBottomSheetState(),
                onDismissRequest = {
                    viewModel.onEvent(
                        AddExpenseEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                },
                onItemSelect = { category ->
                    viewModel.onEvent(
                        AddExpenseEvents.SelectCategory(
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
                        AddExpenseEvents.SelectCategory(
                            categoryName = states.category,
                            alertBoxState = true,
                            bottomSheetState = true
                        )
                    )
                },
                selectedCategory = states.category,
                onClearSelection = {
                    viewModel.onEvent(
                        AddExpenseEvents.SelectCategory(
                            categoryName = "",
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                }
            )
        }

        if(states.alertBoxState){
            CustomTextAlertBox(
                selectedCategory = states.category,
                onCategoryChange = {
                    viewModel.onEvent(
                        AddExpenseEvents.SelectCategory(
                            categoryName = it,
                            bottomSheetState = true,
                            alertBoxState = true
                        )
                    )
                },
                onDismissRequest = {
                    viewModel.onEvent(
                        AddExpenseEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = true,
                            alertBoxState = false
                        )
                    )
                },
                onSaveCategory = {
                    viewModel.onEvent(
                        AddExpenseEvents.SaveCustomCategories
                    )
                    viewModel.onEvent(
                        AddExpenseEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = false,
                            alertBoxState = false
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Transaction Description
        OutlinedTextField(
            value = states.transactionDescription,
            onValueChange = {
                viewModel.onEvent(
                    AddExpenseEvents.ChangeTransactionDescription(it)
                )
            },
            label = { Text("Enter Item Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2,
            readOnly = true // if save item
        )

        Spacer(modifier = Modifier.height(10.dp))


        // Price
        QuantityPriceRow(
            quantity = states.transactionQuantity,
            price = states.transactionPrice,
            onPriceChange = {
                viewModel.onEvent(
                    AddExpenseEvents.ChangeTransactionPrice(it)
                )
                viewModel.onEvent(
                    AddExpenseEvents.SetTransactionFinalPrice(price = it, quantity = states.transactionQuantity)
                )
            },
            onQuantityChange = {
                viewModel.onEvent(
                    AddExpenseEvents.ChangeTransactionQuantity(it)
                )
                viewModel.onEvent(
                    AddExpenseEvents.SetTransactionFinalPrice(price = states.transactionPrice, quantity = it)
                )
            },
            finalPrice = states.transactionFinalPrice,
            currencySymbol = states.transactionCurrencySymbol
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
                   AddExpenseEvents.ChangeTransactionCurrency(
                        currencyName = states.transactionCurrencyName,
                        currencyCode = states.transactionCurrencyCode,
                        currencySymbol = states.transactionCurrencySymbol,
                        currencyExpanded = it
                    )
                )
                if (it) {
                    viewModel.onEvent(AddExpenseEvents.LoadCurrenciesList)
                }
            },
            onDismissRequest = {
                viewModel.onEvent(
                    AddExpenseEvents.ChangeTransactionCurrency(
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
                    AddExpenseEvents.ChangeTransactionCurrency(
                        currencyName = currencyName,
                        currencyCode = currencyCode,
                        currencySymbol = currencySymbol,
                        currencyExpanded = false
                    )
                )

                viewModel.onEvent(
                    AddExpenseEvents.ShowConversion(true)
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
                        AddExpenseEvents.SetConvertedTransactionPrice(price = states.transactionFinalPrice,rate = states.transactionExchangeRate)
                    )
                },
                baseCurrencySymbol = states.baseCurrencySymbol,
                transactionCurrencySymbol = states.transactionCurrencySymbol
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.onEvent(
                    AddExpenseEvents.AddExpenseTransaction
                )
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Add")
        }

    }
}