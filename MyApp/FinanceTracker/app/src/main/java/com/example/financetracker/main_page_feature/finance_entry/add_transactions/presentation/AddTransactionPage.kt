package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.utils.Screens
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

    LaunchedEffect(key1 = context) {
        addTransactionValidationEvents.collect { event ->
            when (event) {
                is AddTransactionViewModel.AddTransactionEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage,Toast.LENGTH_SHORT).show()
                }
                AddTransactionViewModel.AddTransactionEvent.Success -> {
                    Toast.makeText(context,"Transaction Successfully Added",Toast.LENGTH_LONG).show()
                    navController.navigate(route = Screens.HomePageScreen.routes)
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

        Spacer(modifier = Modifier.height(5.dp))

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

        CustomSwitch(
            text = "Saved Item?",
            isCheck = states.saveItemState,
            onCheckChange = {
                viewModel.onEvent(AddTransactionEvents.ChangeSavedItemState(it))
                viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(""))
            }
        )

        Spacer(modifier = Modifier.height(10.dp))



        if (states.saveItemState) {
            OutlinedTextField(
                value = states.transactionName,
                onValueChange = {
                    viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(it))
                },
                label = { Text("Search for items") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            OutlinedTextField(
                value = states.transactionName,
                onValueChange = {
                    viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(it))
                },
                label = { Text("Enter new item name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        SimpleDropdownMenu(
            label = "Select Transaction Type",
            selectedText = states.transactionType,
            expanded = states.transactionTypeExpanded,
            list = states.transactionTypeList,
            onDismissRequest = {
                viewModel.onEvent(
                    AddTransactionEvents.SelectTransactionType(
                        type = states.transactionType,
                        expanded = false
                    )
                )
            },
            onExpandedChange = {
                viewModel.onEvent(
                    AddTransactionEvents.SelectTransactionType(
                        type = states.transactionType,
                        expanded = true
                    )
                )
            },
            displayText = {type ->
                type
            },
            onItemSelect = { type->
                viewModel.onEvent(
                    AddTransactionEvents.SelectTransactionType(
                        type = type,
                        expanded = false
                    )
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

        // Dropdown button to open Bottom Sheet
        OutlinedButton(
            onClick = {

                if(states.transactionType.isNotEmpty()){
                    viewModel.onEvent(AddTransactionEvents.LoadCategory(type = states.transactionType))

                    viewModel.onEvent(
                        AddTransactionEvents.SelectCategory(
                            categoryName = states.category,
                            bottomSheetState = true,
                            alertBoxState = states.alertBoxState
                        )
                    )
                }
                else{
                    viewModel.onEvent(AddTransactionEvents.LoadCategory(type = states.transactionType))
                }
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
                }
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
            maxLines = 2
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
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Text(
                    text = states.transactionCurrencySymbol, // Change this to $, €, etc.
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
                    AddTransactionEvents.ShowConversion(true)
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
                },
                baseCurrencySymbol = states.baseCurrencySymbol,
                transactionCurrencySymbol = states.transactionCurrencySymbol
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