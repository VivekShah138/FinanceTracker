package com.example.financetracker.main_page_feature.view_records.saved_items.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsEvents
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsStates
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateItemDetailsBottomSheet(
    viewModel: ViewSavedItemsViewModel,
    states: ViewSavedItemsStates,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit
) {

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .imePadding()
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Enter Item Details", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = states.updatedItemName,
                onValueChange = {
                    viewModel.onEvent(
                        ViewSavedItemsEvents.ChangeSavedItem(
                            itemName = it,
                            itemPrice = states.updatedItemPrice,
                            itemDescription = states.updatedItemDescription,
                            itemShopName = states.updatedItemShopName
                        )
                    )
                },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = states.updatedItemPrice,
                onValueChange = {
                    viewModel.onEvent(
                        ViewSavedItemsEvents.ChangeSavedItem(
                            itemName = states.updatedItemName,
                            itemPrice = it,
                            itemDescription = states.updatedItemDescription,
                            itemShopName = states.updatedItemShopName
                        )
                    )
                },
                label = { Text("Item Price") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Text(
                        text = states.updatedItemCurrencySymbol,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = states.updatedItemDescription,
                onValueChange = {
                    viewModel.onEvent(
                        ViewSavedItemsEvents.ChangeSavedItem(
                            itemName = states.updatedItemName,
                            itemPrice = states.updatedItemPrice,
                            itemDescription = it,
                            itemShopName = states.updatedItemShopName
                        )
                    )
                },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = states.updatedItemShopName,
                onValueChange = {
                    viewModel.onEvent(
                        ViewSavedItemsEvents.ChangeSavedItem(
                            itemName = states.updatedItemName,
                            itemPrice = states.updatedItemPrice,
                            itemDescription = states.updatedItemDescription,
                            itemShopName = it
                        )
                    )
                },
                label = { Text("Shop Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onSaveClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ItemDetailsBottomSheetPreview() {
//    UpdateItemDetailsBottomSheet(
//        sheetState = rememberModalBottomSheetState(),
//        onDismiss = {},
//        onSaveClick = {},
//        states = ViewSavedItemsStates(
//            updatedItemName = "Milk",
//            updatedItemPrice = "5.0",
//            updatedItemDescription = "1.5L",
//            updatedItemShopName = "Tesco",
//            updatedItemCurrencySymbol = "£"
//        )
//    )
//}

