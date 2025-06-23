package com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financetracker.core.core_presentation.utils.Screens
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel
import com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation.SavedItemViewModel
import com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation.SavedItemsEvent
import com.example.financetracker.setup_account.presentation.components.SimpleDropdownMenu
import kotlin.math.sin

@Composable
fun SavedItemPage(
    viewModel: SavedItemViewModel,
    navController: NavController
) {

    val states by viewModel.savedItemsState.collectAsStateWithLifecycle()
    val savedItemsValidationEvent = viewModel.savedItemsValidationEvents
    val scrollState = rememberScrollState()
    val context = LocalContext.current


    LaunchedEffect(key1 = context) {
        savedItemsValidationEvent.collect { event ->
            when (event) {
                is AddTransactionViewModel.AddTransactionValidationEvent.Failure -> {
                    Toast.makeText(context,event.errorMessage, Toast.LENGTH_SHORT).show()
                }
                AddTransactionViewModel.AddTransactionValidationEvent.Success -> {
                    Toast.makeText(context,"Item Successfully Added", Toast.LENGTH_LONG).show()
                    navController.navigate(route = Screens.HomePageScreen.routes)
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = states.itemName,
            onValueChange = {
                viewModel.onEvent(
                    SavedItemsEvent.OnChangeItemName(it)
                )
            },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        SimpleDropdownMenu(
            label = "Item Currency",
            selectedText = "${states.itemCurrencyName} (${states.itemCurrencyCode})",
            expanded = states.itemCurrencyExpanded,
            list = states.itemCurrenciesList,
            onExpandedChange = {

                viewModel.onEvent(SavedItemsEvent.LoadCurrencies)

                viewModel.onEvent(
                    SavedItemsEvent.OnChangeItemCurrency(
                        name = states.itemCurrencyName,
                        symbol = states.itemCurrencySymbol,
                        code = states.itemCurrencyCode,
                        expanded = true
                    )
                )
            },
            onDismissRequest = {
                viewModel.onEvent(
                    SavedItemsEvent.OnChangeItemCurrency(
                        name = states.itemCurrencyName,
                        symbol = states.itemCurrencySymbol,
                        code = states.itemCurrencyCode,
                        expanded = false
                    )
                )
            },
            onItemSelect = {
                val firstCurrency = it.currencies?.entries?.firstOrNull()
                val currencyName = firstCurrency?.value?.name ?: "N/A"
                val currencySymbol = firstCurrency?.value?.symbol ?: "N/A"
                val currencyCode = firstCurrency?.key ?: "N/A"

                viewModel.onEvent(
                    SavedItemsEvent.OnChangeItemCurrency(
                        name = currencyName,
                        symbol = currencySymbol,
                        code = currencyCode,
                        expanded = false
                    )
                )
            },
            displayText = {
                it.currencies?.entries?.firstOrNull()?.value?.name ?: "N/A"
            }
        )


        OutlinedTextField(
            value = states.itemPrice,
            onValueChange = {
                viewModel.onEvent(
                    SavedItemsEvent.OnChangeItemPrice(it)
                )
            },
            label = { Text("Item Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Text(
                    text = states.itemCurrencySymbol,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        )

        OutlinedTextField(
            value = states.itemDescription,
            onValueChange = {
                viewModel.onEvent(
                    SavedItemsEvent.OnChangeItemDescription(it)
                )
            },
            label = { Text("Item Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = states.itemShopName,
            onValueChange = {
                viewModel.onEvent(
                    SavedItemsEvent.OnChangeItemShopName(it)
                )
            },
            label = { Text("Shop Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.onEvent(
                    SavedItemsEvent.Submit
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Item")
        }
    }
}