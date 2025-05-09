package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import java.text.SimpleDateFormat
import java.util.Locale

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun SearchableListPreview(){

//    SavedItemsCard(
//        itemName = "Milk",
//        itemId = "1",
//        itemDescription = "4 pints(2.2L)",
//        price = "1.45",
//        shopName = "Tesco",
//        currencySymbol = "£",
//        onClick = {}
//    )

    TransactionItemCard(
        Transactions(
            transactionId = 1,
            amount = 1.45,
            currency = null,
            convertedAmount = null,
            exchangeRate = null,
            transactionType = "Expense",
            category = "Groceries",
            dateTime = 1744486763759,
            userUid = "He",
            description = "ItemDescription",
            isRecurring = false,
            cloudSync = false,
            transactionName = "Milk"
        ),
        onClick = {},
        isSelectionMode = true,
        isSelected = true,
        onLongClick = {}
    )
}


//@Composable
//fun TransactionItemCard(
//    item: Transactions,
//    onClick: () -> Unit
//) {
//    val formattedDate = remember(item.dateTime) {
//        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.dateTime)
//    }
//    val priceColor = if (item.transactionType == "Expense") {
//        Color.Red
//    } else {
//        Color.Green
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .padding(horizontal = 16.dp)
//            .clickable { onClick() },
//        shape = RoundedCornerShape(5.dp),
//        elevation = CardDefaults.cardElevation(5.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface
//        )
//    ){
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Formatted date
//            Text(
//                text = formattedDate,
//                modifier = Modifier.weight(1f),
//                fontSize = 12.sp,
//                textAlign = TextAlign.Center
//            )
//
//            // Name + Description in one column
//            Column(
//                modifier = Modifier.weight(2f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = item.transactionName,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = item.description ?: "N/A",
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth(),
//                    fontSize = 12.sp,
//                    color = Color.Gray,
//                    maxLines = 1,  // Limit to 1 line
//                    overflow = TextOverflow.Ellipsis // Show ellipsis when overflow occurs
//                )
//            }
//
//            // Amount
//            Text(
//                text = "${item.currency?.entries?.firstOrNull()?.value?.symbol ?: "$"} ${item.amount}",
//                color = priceColor,
//                modifier = Modifier.weight(1f),
//                textAlign = TextAlign.Center,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionItemCard(
    item: Transactions,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val formattedDate = remember(item.dateTime) {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.dateTime)
    }

    val priceColor = if (item.transactionType == "Expense") Color(0xFFD32F2F) else Color(0xFF2E7D32)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE0F7FA) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox for selection mode
            if (isSelectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onClick() },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // Formatted date
            Text(
                text = formattedDate,
                modifier = Modifier.weight(1f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            // Name + Description
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.transactionName,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.description ?: "N/A",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Amount
            Text(
                text = "${item.currency?.entries?.firstOrNull()?.value?.symbol ?: "$"} ${item.amount}",
                color = priceColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}





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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .combinedClickable (
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE0F7FA) else MaterialTheme.colorScheme.surface
        )
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
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // Left: ID
            Text(
                text = item.itemId.toString(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
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
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.itemDescription ?: "N/A",
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = item.itemShopName ?: "N/A",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
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
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = item.itemPrice.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

