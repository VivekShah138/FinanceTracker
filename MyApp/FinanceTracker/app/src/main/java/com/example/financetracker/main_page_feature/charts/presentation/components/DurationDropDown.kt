package com.example.financetracker.main_page_feature.charts.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun DurationDropDown(
    onRangeDropDownClick: () -> Unit,
    rangeDropDownExpanded: Boolean,
    onRangeDropDownDismiss: () -> Unit,
    filterOptions: List<String>,
    selectedType: String,
    onDurationSelected: (String) -> Unit
){

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Box {
            TextButton(
                onClick = {
                    onRangeDropDownClick()
                }
            ) {
                Text(text = selectedType)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }

            DropdownMenu(
                expanded = rangeDropDownExpanded,
                onDismissRequest = { onRangeDropDownDismiss() }
            ) {
                filterOptions.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option)
                        },
                        onClick = {
                            onDurationSelected(option)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DurationDropDownPreview(){
    DurationDropDown(
       onRangeDropDownDismiss = {},
        onRangeDropDownClick = {},
        onDurationSelected = {},
        rangeDropDownExpanded = false,
        selectedType = "Expense",
        filterOptions = listOf("Expense","Income")
    )
}
