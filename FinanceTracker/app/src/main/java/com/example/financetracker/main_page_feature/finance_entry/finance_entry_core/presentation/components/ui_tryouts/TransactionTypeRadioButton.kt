package com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components.ui_tryouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

@Composable
fun TransactionTypeRadioButton(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        listOf("Expense", "Income").forEach { type ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTypeSelected(type) }
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = type == selectedType,
                    onClick = { onTypeSelected(type) }
                )
                Text(text = type)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonPreview() {
    var selectedType by remember { mutableStateOf("Expense") }

    TransactionTypeRadioButton(
        selectedType = selectedType,
        onTypeSelected = { selectedType = it }
    )
}
