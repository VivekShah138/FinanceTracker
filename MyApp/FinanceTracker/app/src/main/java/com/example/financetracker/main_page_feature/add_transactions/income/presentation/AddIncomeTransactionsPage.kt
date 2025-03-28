package com.example.financetracker.main_page_feature.add_transactions.income.presentation

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.core.presentation.utils.Screens

@Composable
fun AddIncomeTransactionsPage(){
    Text("Income Transactions Screen", modifier = Modifier.fillMaxSize())
//    AddTransactionScreen()


}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddIncomeTransactionsPage() {
//    val categories = listOf("Food", "Transport", "Rent", "Shopping", "Salary")
//    var selectedCategory by remember { mutableStateOf(categories.first()) }
//    var showBottomSheet by remember { mutableStateOf(false) }
//    val sheetState = rememberModalBottomSheetState()
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("Add Transaction") }) }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp)
//        ) {
//            Text("Category", style = MaterialTheme.typography.bodyLarge)
//
//            // Dropdown button to open Bottom Sheet
//            OutlinedButton(
//                onClick = { showBottomSheet = true },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                border = BorderStroke(1.dp, Color.Gray),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(selectedCategory, fontSize = 16.sp, color = Color.Black)
//                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
//                }
//            }
//        }
//    }
//
//    // Modal Bottom Sheet for Category Selection
//    if (showBottomSheet) {
//        ModalBottomSheet(
//            onDismissRequest = { showBottomSheet = false },
//            sheetState = sheetState
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text("Select a Category", style = MaterialTheme.typography.titleMedium)
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                categories.forEach { category ->
//                    TextButton(
//                        onClick = {
//                            selectedCategory = category // Update selection
//                            showBottomSheet = false    // Close bottom sheet
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(category, fontSize = 18.sp)
//                    }
//                }
//            }
//        }
//    }
//}
//
