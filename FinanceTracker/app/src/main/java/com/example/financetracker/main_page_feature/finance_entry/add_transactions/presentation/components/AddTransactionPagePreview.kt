package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components

import TransactionTypeSegmentedButton
import TransactionTypeSegmentedButton2
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.impl.utils.forAll
import com.example.financetracker.setup_account.presentation.components.CustomSwitch
import com.example.financetracker.setup_account.presentation.components.SimpleDropdownMenu


@Preview(
    showBackground = true,
    showSystemUi = true
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionPagePreview(){

    val isFocused = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        if(isFocused.value) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp) // Padding to avoid touching the screen's top edge

            ) {
                // Search Bar
                OutlinedTextField(
                    value = "states.transactionName",
                    onValueChange = {
//                        viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(it))
//                        viewModel.onEvent(
//                            AddTransactionEvents.FilterSavedItemList(
//                                list = states.transactionSearchList,
//                                newWord = it
//                            )
//                        )
                    },
                    singleLine = true,
                    label = { Text("Search for items") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isFocused.value = it.isFocused }
//                        .focusRequester(focusRequester)
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )

            }
        }
        else{

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ){

                    Spacer(modifier = Modifier.height(5.dp))

                    CustomSwitch(
                        text = "Recurring Transaction?",
                        isCheck = false,
                        onCheckChange = {}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

//            Text("Category", style = MaterialTheme.typography.bodyLarge)
                    CustomSwitch(
                        text = "Saved Item?",
                        isCheck = false,
                        onCheckChange = {}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TransactionTypeSegmentedButton2 (
                        selectedType = "Expense",
                        onTypeSelected = {}
                    )

//                    Spacer(modifier = Modifier.height(10.dp))

                    TransactionCategoryButton(
                        category = "",
                        onClick = {}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Search Bar or Text Input based on Toggle
                    // Outlined Text Field for Search or Input Mode
                    if (false) {
                        OutlinedTextField(
                            value = "searchQuery",
                            onValueChange = {  },
                            label = { Text("Search For Items") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        OutlinedTextField(
                            value = "newItemText",
                            onValueChange = {  },
                            label = { Text("Enter Item Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Description
                    OutlinedTextField(
                        value = "newItemText",
                        onValueChange = {  },
                        label = { Text("Enter Item Description") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        readOnly = true // if save item
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Price
                    OutlinedTextField(
                        value = "finalPrice",
                        onValueChange = {},  // Read-only field
                        label = { Text("Final Price") },
                        readOnly = true,
                        textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp), // Controls the space between the icon and the text
                            ) {
                                Text(
                                    text = "$", // Display the currency symbol
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Transaction Currency
                    SimpleDropdownMenu(label = "Base Currency",selectedText = "Rupee", expanded = false,
                        list = emptyList<String>(), onExpandedChange = {}, onDismissRequest = {}, displayText = { it }, onItemSelect = {})

                    Spacer(modifier = Modifier.height(10.dp))

                    CurrencyConversionSection(exchangeRate = "100", convertedAmount = "100", onConvertClick = {}, baseCurrencySymbol = "&", transactionCurrencySymbol = "$", isConverted = true)

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Text("Add")
                    }
                }
            }

        }
    }
}


@Composable
fun QuantityPriceRow(
    quantity: String,
    onQuantityChange: (String) -> Unit,
    price: String,
    finalPrice: String,
    onPriceChange: (String) -> Unit,
    currencySymbol: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = quantity,
            onValueChange = onQuantityChange,
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = price,
            onValueChange = onPriceChange,
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
        )
    }

    OutlinedTextField(
        value = finalPrice,
        onValueChange = {},  // Read-only field
        label = { Text("Final Price") },
        readOnly = true,
        textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Text(
                text = currencySymbol, // Change this to $, €, etc.
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
fun CurrencyConversionSection(
    exchangeRate: String,
    convertedAmount: String,
    onConvertClick: () -> Unit,
    baseCurrencySymbol: String,
    transactionCurrencySymbol: String,
    isConverted: Boolean
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // If conversion is not done, show the "Convert" button
        if (!isConverted) {
            TextButton(
                onClick = {
                    onConvertClick()
                }
            ) {
                Text("Convert to $baseCurrencySymbol")
            }
        } else {
            // After conversion, show the converted amount
            OutlinedTextField(
                value = convertedAmount,
                onValueChange = {},  // Read-only field
                label = { Text("Converted Price") },
                readOnly = true,
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text(
                        text = baseCurrencySymbol, // Display the currency symbol
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){

                // Display Exchange Rate information
                Text(
                    text = "Exchange Rate:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(2.dp))
                // Display Exchange Rate information
                Text(
                    text = "1$baseCurrencySymbol = $exchangeRate$transactionCurrencySymbol",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

            }
        }
    }

}

