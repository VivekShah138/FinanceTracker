package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

// Required Imports
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//@Composable
//fun FilterBottomSheet(
//    sortOrder: String,
//    onSortOrderChange: (String) -> Unit,
//    type: String,
//    onTypeChange: (String) -> Unit,
//    selectedCategories: List<String>,
//    onCategoryToggle: (String) -> Unit,
//    allCategories: List<String>
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        // Sort By
//        Text(text = "Sort By", style = MaterialTheme.typography.titleMedium)
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(vertical = 8.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .clickable { onSortOrderChange("Ascending") }
//                    .padding(end = 16.dp)
//            ) {
//                RadioButton(
//                    selected = sortOrder == "Ascending",
//                    onClick = { onSortOrderChange("Ascending") }
//                )
//                Text("Ascending")
//            }
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.clickable { onSortOrderChange("Descending") }
//            ) {
//                RadioButton(
//                    selected = sortOrder == "Descending",
//                    onClick = { onSortOrderChange("Descending") }
//                )
//                Text("Descending")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Type
//        Text(text = "Type", style = MaterialTheme.typography.titleMedium)
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(vertical = 8.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .clickable { onTypeChange("Income") }
//                    .padding(end = 16.dp)
//            ) {
//                RadioButton(
//                    selected = type == "Income",
//                    onClick = { onTypeChange("Income") }
//                )
//                Text("Income")
//            }
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.clickable { onTypeChange("Expense") }
//            ) {
//                RadioButton(
//                    selected = type == "Expense",
//                    onClick = { onTypeChange("Expense") }
//                )
//                Text("Expense")
//            }
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.clickable { onTypeChange("Both") }
//            ) {
//                RadioButton(
//                    selected = type == "Both",
//                    onClick = { onTypeChange("Both") }
//                )
//                Text("Both")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Category
//        Text(text = "Category", style = MaterialTheme.typography.titleMedium)
//        Column(modifier = Modifier.padding(top = 8.dp)) {
//            allCategories.forEach { category ->
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { onCategoryToggle(category) }
//                        .padding(vertical = 4.dp)
//                ) {
//                    Checkbox(
//                        checked = selectedCategories.contains(category),
//                        onCheckedChange = { onCategoryToggle(category) }
//                    )
//                    Text(text = category)
//                }
//            }
//        }
//    }
//}
//
//// --- Preview ---
//@Preview(showBackground = true)
//@Composable
//fun FilterBottomSheetPreview() {
//    var sortOrder by remember { mutableStateOf("Ascending") }
//    var type by remember { mutableStateOf("Income") }
//    var selectedCategories by remember { mutableStateOf(listOf<String>()) }
//
//    val allCategories = listOf("Food", "Transport", "Shopping", "Bills", "Other")
//
//    Surface {
//        FilterBottomSheet(
//            sortOrder = sortOrder,
//            onSortOrderChange = { sortOrder = it },
//            type = type,
//            onTypeChange = { type = it },
//            selectedCategories = selectedCategories,
//            onCategoryToggle = { category ->
//                selectedCategories = if (selectedCategories.contains(category)) {
//                    selectedCategories - category
//                } else {
//                    selectedCategories + category
//                }
//            },
//            allCategories = allCategories
//        )
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetModal(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    sortOrder: String,
    onSortOrderChange: (String) -> Unit,
    type: String,
    onTypeChange: (String) -> Unit,
    selectedCategories: List<String>,
    onCategoryToggle: (String) -> Unit,
    allCategories: List<String>
) {
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            FilterBottomSheet(
                sortOrder = sortOrder,
                onSortOrderChange = onSortOrderChange,
                type = type,
                onTypeChange = onTypeChange,
                selectedCategories = selectedCategories,
                onCategoryToggle = onCategoryToggle,
                allCategories = allCategories
            )
        }
    }
}

@Composable
fun FilterBottomSheet(
    sortOrder: String,
    onSortOrderChange: (String) -> Unit,
    type: String,
    onTypeChange: (String) -> Unit,
    selectedCategories: List<String>,
    onCategoryToggle: (String) -> Unit,
    allCategories: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Sort By
        Text(text = "Sort By", style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            listOf("Ascending", "Descending").forEach { order ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onSortOrderChange(order) }
                ) {
                    RadioButton(
                        selected = sortOrder == order,
                        onClick = { onSortOrderChange(order) }
                    )
                    Text(order)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Type
        Text(text = "Type", style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            listOf("Income", "Expense", "Both").forEach { t ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onTypeChange(t) }
                        .padding(end = 16.dp)
                        .weight(1f)
                ) {
                    RadioButton(
                        selected = type == t,
                        onClick = { onTypeChange(t) }
                    )
                    Text(t, modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category
        Text(text = "Category", style = MaterialTheme.typography.titleMedium)
        Column(modifier = Modifier.padding(top = 8.dp)) {
            allCategories.forEach { category ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategoryToggle(category) }
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = selectedCategories.contains(category),
                        onCheckedChange = { onCategoryToggle(category) }
                    )
                    Text(text = category)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
