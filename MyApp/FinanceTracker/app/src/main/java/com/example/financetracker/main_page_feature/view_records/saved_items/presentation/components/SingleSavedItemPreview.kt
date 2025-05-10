package com.example.financetracker.main_page_feature.view_records.saved_items.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.core.core_presentation.components.AppTopBar
import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsEvents
import com.example.financetracker.setup_account.presentation.components.SimpleDropdownMenu


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SingleSavedItemPreview(){

    Scaffold(

        topBar = {
            AppTopBar(
                title = "Item Details",
                showBackButton = true,
                showMenu = false,
                onBackClick = {
//                    navController.popBackStack()
//                    if(states.isSelectionMode){
//                        viewTransactionsViewModel.onEvent(ViewTransactionsEvents.ExitSelectionMode)
//                    }
                },
                customActions = {
                    IconButton(onClick = { /* handle delete */ }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }

    ) {paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            OutlinedTextField(
                value = "itemName",
                onValueChange = { },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = "Item Currency",
                onValueChange = {  },
                label = { Text("Item Currency") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
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
                Text("Edit")
            }
        }

    }
}