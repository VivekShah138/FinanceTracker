package com.example.financetracker.presentation.features.finance_entry_feature.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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

