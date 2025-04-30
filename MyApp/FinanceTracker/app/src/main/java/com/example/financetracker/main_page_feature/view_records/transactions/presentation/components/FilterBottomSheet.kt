package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

// Required Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionOrder
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionTypeFilter


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
    allCategories: List<String>,
    onClearAll: () -> Unit,
    onApply: () -> Unit

) {
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            FilterBottomSheet2(
                sortOrder = sortOrder,
                onSortOrderChange = onSortOrderChange,
                type = type,
                onTypeChange = onTypeChange,
                selectedCategories = selectedCategories,
                onCategoryToggle = onCategoryToggle,
                allCategories = allCategories,
                onApply = onApply,
                onClearAll = onClearAll
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetModal2(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    filters: List<TransactionFilter>,
    onFilterChange: (TransactionFilter) -> Unit,
    onClearAll: () -> Unit,
    onApply: () -> Unit,
    allCategories: List<Category>

) {
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            FilterBottomSheet3(
                filters = filters,
                onFilterChange = onFilterChange,
                allCategories = allCategories,
                onApply = onApply,
                onClearAll = onClearAll
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


@Composable
fun FilterBottomSheet2(
    sortOrder: String,
    onSortOrderChange: (String) -> Unit,
    type: String,
    onTypeChange: (String) -> Unit,
    selectedCategories: List<String>,
    onCategoryToggle: (String) -> Unit,
    allCategories: List<String>,
    onClearAll: () -> Unit,
    onApply: () -> Unit
) {
    var selectedSection by remember { mutableStateOf("Sort By") }
    val categoryScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            // Left Panel - Section Titles
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant) // Tint left side
            ) {
                listOf("Sort By", "Type", "Category").forEach { section ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (selectedSection == section)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.Transparent
                            )
                            .clickable { selectedSection = section }
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                    ) {
                        Text(
                            text = section,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = if (selectedSection == section)
                                    MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }

                }
            }

            // Right Panel - Options
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
            ) {
                when (selectedSection) {
                    "Sort By" -> {
                        listOf("Ascending", "Descending").forEach { order ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSortOrderChange(order) }
                                    .padding(vertical = 6.dp)
                            ) {
                                RadioButton(
                                    selected = sortOrder == order,
                                    onClick = { onSortOrderChange(order) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(order)
                            }
                        }
                    }

                    "Type" -> {
                        listOf("Income", "Expense", "Both").forEach { t ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onTypeChange(t) }
                                    .padding(vertical = 6.dp)
                            ) {
                                RadioButton(
                                    selected = type == t,
                                    onClick = { onTypeChange(t) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(t)
                            }
                        }
                    }

                    "Category" -> {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .verticalScroll(categoryScrollState)
                        ) {
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
                                        onCheckedChange = { onCategoryToggle(category) },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(text = category)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Optional spacing between buttons
        ) {
            Button(
                onClick = onClearAll,
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear All")
            }
            Button(
                onClick = onApply,
                modifier = Modifier.weight(1f)
            ) {
                Text("Apply")
            }
        }
    }
}

@Composable
fun FilterBottomSheet3(
    filters: List<TransactionFilter>,
    onFilterChange: (TransactionFilter) -> Unit,
    onClearAll: () -> Unit,
    onApply: () -> Unit,
    allCategories: List<Category>
) {
    var selectedSection by remember { mutableStateOf("Sort By") }
    val categoryScrollState = rememberScrollState()

    // Extract current filter values
    val currentSortOrder = filters.filterIsInstance<TransactionFilter.Order>().firstOrNull()?.order
    val currentType = filters.filterIsInstance<TransactionFilter.TransactionType>().firstOrNull()?.type
    val currentCategories = filters.filterIsInstance<TransactionFilter.Category>().firstOrNull()?.selectedCategories ?: allCategories

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            // Left Panel - Section Titles
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                listOf("Sort By", "Type", "Category").forEach { section ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (selectedSection == section)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.Transparent
                            )
                            .clickable { selectedSection = section }
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                    ) {
                        Text(
                            text = section,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = if (selectedSection == section)
                                    MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }

            // Right Panel - Options
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
            ) {
                when (selectedSection) {
                    "Sort By" -> {
                        listOf(TransactionOrder.Ascending, TransactionOrder.Descending).forEach { order ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onFilterChange(TransactionFilter.Order(order))
                                    }
                                    .padding(vertical = 6.dp)
                            ) {
                                RadioButton(
                                    selected = currentSortOrder == order,
                                    onClick = {
                                        onFilterChange(TransactionFilter.Order(order))
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(order.label)
                            }
                        }
                    }

                    "Type" -> {
                        listOf(
                            TransactionTypeFilter.Income,
                            TransactionTypeFilter.Expense,
                            TransactionTypeFilter.Both
                        ).forEach { type ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onFilterChange(TransactionFilter.TransactionType(type))
                                    }
                                    .padding(vertical = 6.dp)
                            ) {
                                RadioButton(
                                    selected = currentType == type,
                                    onClick = {
                                        onFilterChange(TransactionFilter.TransactionType(type))
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(type.label)
                            }
                        }
                    }

                    "Category" -> {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .verticalScroll(categoryScrollState)
                        ) {
                            allCategories.forEach { category ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val updated = if (currentCategories.contains(category)) {
                                                currentCategories - category
                                            } else {
                                                currentCategories + category
                                            }
                                            onFilterChange(TransactionFilter.Category(updated))
                                        }
                                        .padding(vertical = 4.dp)
                                ) {
                                    Checkbox(
                                        checked = currentCategories.contains(category),
                                        onCheckedChange = {
                                            val updated = if (currentCategories.contains(category)) {
                                                currentCategories - category
                                            } else {
                                                currentCategories + category
                                            }
                                            onFilterChange(TransactionFilter.Category(updated))
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(text = category.name) // assuming Category has a `name` field
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onClearAll,
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear All")
            }
            Button(
                onClick = onApply,
                modifier = Modifier.weight(1f)
            ) {
                Text("Apply")
            }
        }
    }
}

