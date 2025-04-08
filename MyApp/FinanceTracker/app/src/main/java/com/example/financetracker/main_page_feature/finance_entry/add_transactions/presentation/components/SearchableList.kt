package com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun SearchableListPreview(){
//    SearchableList (
//        onClick = {
//
//        },
//        itemName = "Milk",
//        itemShopName = "Tesco",
//        itemDescription = "4 pints(2.2L)",
//        itemCurrencySymbol = "£",
//        itemPrice = "1.45"
//    )
    ItemCard(
        itemName = "Milk",
        itemId = "1",
        itemDescription = "4 pints(2.2L)",
        price = "1.45",
        shopName = "Tesco",
        onClick = {}
    )
}


@Composable
fun SearchableList(
    onClick: () -> Unit,
    itemName: String,
    itemShopName: String,
    itemDescription: String,
    itemCurrencySymbol: String,
    itemPrice: String
){

    Card (
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium // Rounded corners for a more modern look
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .clickable
                {
                    // Handle item selection
//                viewModel.onEvent(AddTransactionEvents.ChangeTransactionName(item.itemName))
//                viewModel.onEvent(AddTransactionEvents.FilterSavedItemList(emptyList(), ""))
                    onClick()
                }
        ){

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Column(
                    modifier = Modifier.padding(0.dp)
                ) {

                    Text(
                        text = itemName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = itemShopName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = itemDescription,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = itemCurrencySymbol,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = itemPrice,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }

    }
}

@Composable
fun ItemCard(
    itemId: String,
    itemName: String,
    itemDescription: String,
    shopName: String,
    price: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row {
                    Text(
                        text = itemId,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = itemName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(   // Price aligned to end (Top-Right)
                    text = price,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Text(
                text = itemDescription,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = shopName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
