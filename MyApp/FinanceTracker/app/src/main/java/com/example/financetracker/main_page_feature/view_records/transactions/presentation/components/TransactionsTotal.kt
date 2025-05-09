package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale


@Preview(showBackground = true)
@Composable
fun TransactionsTotalPreview(){
    TransactionsTotal(
        currencySymbol = "$",
        amount = -10.00
    )
}


@Composable
fun TransactionsTotal(
    currencySymbol: String,
    amount: Double
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Spending Summary",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium, fontSize = 12.sp),
            color = MaterialTheme.colorScheme.onBackground
        )

        val color = when {
            amount < 0 -> Color(0xFFD32F2F) // red for negative
            amount > 0 -> Color(0xFF2E7D32) // green or your main color
            else -> MaterialTheme.colorScheme.onBackground // neutral for zero
        }

        Text(
            text = String.format(Locale.US, "$currencySymbol %.2f", Math.abs(amount)),
            style = MaterialTheme.typography.headlineMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}