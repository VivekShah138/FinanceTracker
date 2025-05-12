package com.example.financetracker.main_page_feature.finance_entry.finance_entry_core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CarRepair
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.filled.WorkOutline
//import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.financetracker.core.local.domain.room.model.Category


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun <T> CustomBottomSheet(
//    categories: List<T>,
//    sheetState: SheetState,
//    onDismissRequest: () -> Unit,
//    onItemSelect: (T) -> Unit,
//    displayText: (T) -> String,
//    onCustomAddClick: () -> Unit,
//    selectedCategory: String, // Track selected category
//    onClearSelection: () -> Unit // Callback to clear the selected category
//)
fun CustomBottomSheet(
    categories: List<Category>,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onItemSelect: (Category) -> Unit,
    displayText: (Category) -> String,
    onCustomAddClick: () -> Unit,
    selectedCategory: String, // Track selected category
    onClearSelection: () -> Unit // Callback to clear the selected category
)
{
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Row for "Select a Category" and "Clear" button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select a Category",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f) // Pushes the next item to the right
                )

                // Show "Clear" option only if a category is selected
                if (selectedCategory.isNotEmpty()) {
                    Text(
                        text = "Clear",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { onClearSelection() }
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 columns
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onItemSelect(category) },
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp) // Shadow effect
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Category,
//                                contentDescription = null,
//                                modifier = Modifier.size(40.dp)
//                            )
                            Icon(
                                imageVector = if(category.isCustom) {
                                    Icons.Default.Category // single icon for custom categories
                                } else {
                                    getCategoryIcon(category.name)
                                },
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = displayText(category),
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCustomAddClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Custom Category")
            }
        }
    }
}

fun getCategoryIcon(categoryName: String): ImageVector {
    return when (categoryName.lowercase()) {
        "food" -> Icons.Default.Fastfood
        "travel" -> Icons.Default.DirectionsCar
        "shopping" -> Icons.Default.ShoppingBasket
        "restaurant" -> Icons.Default.Restaurant
        "groceries" -> Icons.Default.LocalGroceryStore
        "rent" -> Icons.Default.Home
        "vehicle maintenance" -> Icons.Default.LocalCarWash // or Icons.Default.CarRepair (if using extended icons)
        "medicine" -> Icons.Default.MedicalServices
        "salary" -> Icons.Default.AttachMoney
        "freelance" -> Icons.Default.WorkOutline
        "business" -> Icons.Default.BusinessCenter
        "studies" -> Icons.Default.School
        "interest" -> Icons.Default.Savings
        else -> Icons.Default.Category
    }
}



