package com.example.financetracker.main_page_feature.add_transactions.expense.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financetracker.main_page_feature.add_transactions.presentation.components.CustomBottomSheet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseTransactionsPage(
    viewModel: AddExpenseViewModel
){
    val states by viewModel.addExpenseStates.collectAsStateWithLifecycle()

//    Text("Expense Transactions Screen")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("Category", style = MaterialTheme.typography.bodyLarge)

        // Dropdown button to open Bottom Sheet
        OutlinedButton(
            onClick = {
                viewModel.onEvent(
                    AddExpenseEvents.SelectCategory(
                        categoryName = states.category,
                        bottomSheetState = true
                    )
                )
                viewModel.onEvent(AddExpenseEvents.LoadCategory)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(states.category, fontSize = 16.sp, color = Color.Black)
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
                            bottomSheetState = false
                        )
                    )
                },
                onItemSelect = { category ->
                    viewModel.onEvent(
                        AddExpenseEvents.SelectCategory(
                            categoryName = category.name,
                            bottomSheetState = false
                        )
                    )
                },
                displayText = {category ->
                    category.name
                }
            )
        }
    }
}