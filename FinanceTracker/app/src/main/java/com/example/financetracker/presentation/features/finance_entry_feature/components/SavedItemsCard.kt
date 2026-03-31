package com.example.financetracker.presentation.features.finance_entry_feature.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.domain.model.Currency
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.ui.theme.FinanceTrackerTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedItemsCard(
    item: SavedItems,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .combinedClickable (
                onClick = onClick,
                onLongClick = onLongClick
            ),
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isSelectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onClick() },
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // Left: ID
            Text(
                text = item.itemId.toString(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
            )

            // Center: Name, Description, Shop
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = item.itemName,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = if(item.itemDescription.isNullOrEmpty()) "No Description" else item.itemDescription,
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = if(item.itemShopName.isNullOrEmpty()) "No Shop Name" else item.itemShopName,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Right: Price with Symbol
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.itemCurrency.entries.firstOrNull()?.value?.symbol ?: "$",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = item.itemPrice.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}




@Preview(
    showBackground = true,
    name = "Selected"
)
@Composable
fun SavedItemsCardPreview() {

    val previewCurrency = mapOf(
        "USD" to Currency(
            name = "US Dollar",
            symbol = "$"
        )
    )

    FinanceTrackerTheme {
        SavedItemsCard(
            item = SavedItems(
                itemId = 1,
                itemName = "Bhavnagri Gathiya",
                itemPrice = 1.45,
                itemDescription = "ItemDescription",
                itemShopName = "Local Shop",
                itemCurrency = previewCurrency,
                userUID = "He",
                cloudSync = false
            ),
            isSelected = true,
            isSelectionMode = true,
            onClick = {},
            onLongClick = {}
        )
    }
}


@Preview(
    showBackground = true,
    name = "Not Selected"
)
@Composable
fun SavedItemsCardPreview2() {

    val previewCurrency = mapOf(
        "USD" to Currency(
            name = "US Dollar",
            symbol = "$"
        )
    )

    FinanceTrackerTheme {
        SavedItemsCard(
            item = SavedItems(
                itemId = 1,
                itemName = "Bhavnagri Gathiya",
                itemPrice = 1.45,
                itemDescription = "ItemDescription",
                itemShopName = "Local Shop",
                itemCurrency = previewCurrency,
                userUID = "He",
                cloudSync = false
            ),
            isSelected = false,
            isSelectionMode = false,
            onClick = {},
            onLongClick = {}
        )
    }
}