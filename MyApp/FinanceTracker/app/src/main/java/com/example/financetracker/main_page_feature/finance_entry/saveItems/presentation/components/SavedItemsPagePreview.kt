package com.example.financetracker.main_page_feature.finance_entry.saveItems.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.setup_account.presentation.components.SimpleDropdownMenu


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SaveItemsPagePreview(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Add Item",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = "itemName",
            onValueChange = { },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )

        SimpleDropdownMenu(
            label = "Item Currency",
            selectedText = "Item Currency",
            expanded = false,
            list = emptyList<String>(),
            onExpandedChange = {},
            onDismissRequest = {},
            onItemSelect = {},
            displayText = {it}
        )


        OutlinedTextField(
            value = "itemPrice",
            onValueChange = {  },
            label = { Text("Item Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = "itemDescription",
            onValueChange = {  },
            label = { Text("Item Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = "itemShopName",
            onValueChange = {  },
            label = { Text("Shop Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Item")
        }
    }
}