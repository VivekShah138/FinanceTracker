package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionCategoryButton(
    category: String,
    onClick: () -> Unit
) {

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Transaction Category",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Dropdown button to open Bottom Sheet
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp), // Adjust vertical padding here
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    category.ifEmpty {
                        "Select a Category"
                    },
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = MaterialTheme.colorScheme.onBackground)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun SegmentedButtonPreview() {

    TransactionCategoryButton (
        category = "",
        onClick = {}
    )
}