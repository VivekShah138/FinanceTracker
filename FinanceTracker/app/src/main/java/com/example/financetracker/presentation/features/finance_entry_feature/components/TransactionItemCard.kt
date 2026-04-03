package com.example.financetracker.presentation.features.finance_entry_feature.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.domain.model.Transactions


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionItemCard(
    item: Transactions,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val priceColor = if (item.transactionType == "Expense") Color(0xFFD32F2F) else Color(0xFF2E7D32)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.padding(start = 16.dp)) {

            Card(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                border = BorderStroke(
                    width = 0.5.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                ),
                colors = CardDefaults.cardColors(containerColor = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getCategoryIcon(item.category),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

        }


        // Name + Description
        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.transactionName,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (item.description.isNullOrEmpty()) "No Description" else item.description,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Amount
        Text(
            text = "${item.currency?.entries?.firstOrNull()?.value?.symbol ?: "$"} ${if (item.convertedAmount != 0.0) item.convertedAmount else item.amount}",
            color = priceColor,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun TransactionCardPreview() {
    TransactionItemCard(
        Transactions(
            transactionId = 1,
            amount = 1.45,
            currency = null,
            convertedAmount = 0.0,
            exchangeRate = 0.0,
            transactionType = "Expense",
            category = "Groceries",
            dateTime = 1744486763759,
            userUid = "He",
            description = "ItemDescription",
            isRecurring = false,
            cloudSync = false,
            transactionName = "Bhavnagri Gathiya"
        ),
        onClick = {},
        isSelected = true,
        onLongClick = {}
    )
}