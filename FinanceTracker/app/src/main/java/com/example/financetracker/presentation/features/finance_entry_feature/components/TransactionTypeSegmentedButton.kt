package com.example.financetracker.presentation.features.finance_entry_feature.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TransactionTypeSegmentedButton(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Transaction Type",
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.offset(x = (-14).dp,y = (-5).dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Expense", "Income").forEach { type ->
                val isSelected = type == selectedType
                Row(
                    modifier = Modifier.clickable {
                        onTypeSelected(type)
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            onTypeSelected(type)
                        }
                    )
                    Text(
                        text = type,
                        textAlign = TextAlign.Start
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedButtonPreview() {
    var selectedType by remember { mutableStateOf("Expense") }

    TransactionTypeSegmentedButton(
        selectedType = selectedType,
        onTypeSelected = { selectedType = it }
    )
}
