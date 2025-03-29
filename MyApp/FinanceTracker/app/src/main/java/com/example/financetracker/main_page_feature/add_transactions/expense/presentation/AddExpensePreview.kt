package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.main_page_feature.add_transactions.presentation.components.CustomBottomSheet
import com.example.financetracker.setup_account.presentation.components.CustomSwitch
import com.example.financetracker.setup_account.presentation.components.SettingsSwitchItem

@Preview(
    showBackground = true,
    showSystemUi = true
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpensePagePreview(){


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

            // Description
            OutlinedTextField(
                value = "newItemText",
                onValueChange = {  },
                label = { Text("Enter Item Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                readOnly = true // if save item
            )


        }
    }
}