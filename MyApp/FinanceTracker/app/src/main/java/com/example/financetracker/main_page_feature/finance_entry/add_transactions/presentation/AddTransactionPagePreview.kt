package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.setup_account.presentation.components.CustomSwitch
import com.example.financetracker.setup_account.presentation.components.SimpleDropdownMenu


@Preview(
    showBackground = true,
    showSystemUi = true
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionPagePreview(){


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


            SimpleDropdownMenu(
                label = "Type",
                selectedText = "Expense",
                expanded = false,
                list = emptyList<String>(),
                onDismissRequest = {},
                onExpandedChange = {},
                displayText = {it},
                onItemSelect = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Dropdown button to open Bottom Sheet
            OutlinedButton(
                onClick = {

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
                    Text("Category", fontSize = 16.sp, color = Color.Black)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
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
                    Text(
                        text = "$", // Change this to $, €, etc.
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Transaction Currency
            SimpleDropdownMenu(label = "Base Currency",selectedText = "Rupee", expanded = false,
                list = emptyList<String>(), onExpandedChange = {}, onDismissRequest = {}, displayText = { it }, onItemSelect = {})

            Spacer(modifier = Modifier.height(10.dp))

            CurrencyConversionSection(exchangeRate = "100", convertedAmount = "100", onConvertClick = {}, baseCurrencySymbol = "&", transactionCurrencySymbol = "$")

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
    transactionCurrencySymbol: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Currency Conversion",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Convert Button
            Button(
                onClick = onConvertClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Convert to $baseCurrencySymbol")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Exchange Rate
            Text(
                text = "Exchange Rate: 1$baseCurrencySymbol = $exchangeRate$transactionCurrencySymbol",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Converted Amount
            Text(
                text = "Converted Amount: $convertedAmount$baseCurrencySymbol",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF388E3C) // Green for emphasis
            )
        }
    }
}

