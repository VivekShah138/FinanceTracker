package com.example.financetracker.main_page_feature.add_transactions.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun <T> CustomBottomSheet(
//    categories: List<T>,
//    sheetState: SheetState,
//    onDismissRequest: () -> Unit,
//    onItemSelect: (T) -> Unit,
//    displayText: (T) -> String
//){
//    ModalBottomSheet(
//        onDismissRequest = onDismissRequest,
//        sheetState = sheetState
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Select a Category", style = MaterialTheme.typography.titleMedium)
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            categories.forEach { category ->
//                TextButton(
//                    onClick = {
//                        onItemSelect(category)
//                    },
////                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    val displayValue = displayText(category)
//                    Text(
//                        text = displayValue,
//                        fontSize = 14.sp
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Button(
//                onClick = {}
//            ) {
//                Text("Add Custom Category")
//            }
//        }
//    }
//}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomBottomSheet(
    categories: List<T>,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onItemSelect: (T) -> Unit,
    displayText: (T) -> String
){
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Select a Category", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 columns
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onItemSelect(category) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Default.Category, contentDescription = null)
                            Text(text = displayText(category), fontSize = 14.sp)
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Custom Category")
            }
        }
    }
}



