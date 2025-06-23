package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.categories_feature.core.presentation.SharedCategoriesEvents
import com.example.financetracker.categories_feature.core.presentation.components.SingleCategoryDisplay2
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components.TransactionItemCard
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter
import com.example.financetracker.ui.theme.AppTheme

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ViewTransactionsPagePreview(){

    AppTheme(dynamicColor = false, darkTheme = true) {


        val transactionList: List<Transactions> = listOf(
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
            Transactions(
                transactionId = 2,
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
            Transactions(
                transactionId = 3,
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
            Transactions(
                transactionId = 4,
                amount = 228.8,
                currency = null,
                convertedAmount = null,
                exchangeRate = null,
                transactionType = "Income",
                category = "Groceries",
                dateTime = 1744486763759,
                userUid = "He",
                description = "ItemDescription",
                isRecurring = false,
                cloudSync = false,
                transactionName = "Salary"
            )
        )
//        val transactionList: List<Transactions> = emptyList()

        val selectedTransactionList: Set<Int> = setOf(4)


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            item {
                TransactionsTotal(
                    currencySymbol = "$",
                    amount = 100.0
                )

                Spacer(modifier = Modifier.height(8.dp))

                DateFilterWithIcon2(
                    onFilterIconClick = {},
                )
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(modifier = Modifier.padding(vertical = 16.dp).fillMaxSize()) {
                        transactionList.forEachIndexed { index, transaction ->

                            val isSelected = selectedTransactionList.contains(transaction.transactionId)

                            Column(modifier = Modifier.fillMaxSize()) {
                                TransactionItemCard(
                                    item = transaction,
                                    onClick = {

                                    },
                                    onLongClick = {

                                    },
                                    isSelected = isSelected,
                                    isSelectionMode = false
                                )
                                if (index < transactionList.lastIndex) {
                                    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.inverseSurface)
                                }
                            }
                        }
                    }
                }
            }



        }

//        Column(modifier = Modifier
//            .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//
//            TransactionsTotal(
//                currencySymbol = "$",
//                amount = 100.0
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            DateFilterWithIcon2(
//                onFilterIconClick = {},
//            )
//
//            LazyColumn {
////            items(transactionList) { transaction ->
////
////                val isSelected = selectedTransactionList.contains(transaction.transactionId)
////
////
////                val priceColor = if (transaction.transactionType == "Expense") {
////                    Color.Red
////                } else {
////                    Color.Green
////                }
////
////                TransactionItemCard(
////                    item = transaction,
////                    onClick = {
////
////                    },
////                    onLongClick = {
////
////                    },
////                    isSelected = isSelected,
////                    isSelectionMode = true
////                )
////            }
////        }
//
//
//                item {
//                    Surface(
//                        modifier = Modifier.fillMaxWidth(),
//                        tonalElevation = 2.dp,
//                        shape = MaterialTheme.shapes.medium,
//                        shadowElevation = 4.dp,
//                        color = MaterialTheme.colorScheme.surface
//                    ) {
//                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
//                            transactionList.forEachIndexed { index, transaction ->
//
//                                val isSelected = selectedTransactionList.contains(transaction.transactionId)
//
//                                Column {
//                                    TransactionItemCard(
//                                        item = transaction,
//                                        onClick = {
//
//                                        },
//                                        onLongClick = {
//
//                                        },
//                                        isSelected = isSelected,
//                                        isSelectionMode = true
//                                    )
//                                    if (index < transactionList.lastIndex) {
//                                        Divider()
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }

    }
}


@Composable
fun DateFilterWithIcon(
    rangeDropDownExpanded: Boolean,
    onRangeDropDownClick: () -> Unit,
    onRangeDropDownDismiss: () -> Unit,
    filterOptions: List<DurationFilter>,
    selectedDuration: DurationFilter,
    onDurationSelected: (DurationFilter) -> Unit,
    onFilterIconClick: () -> Unit,

    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
                    Text(text = selectedDuration.label)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }

                DropdownMenu(
                    expanded = rangeDropDownExpanded,
                    onDismissRequest = { onRangeDropDownDismiss() }
                ) {
                    filterOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(option.label)
                            },
                            onClick = {
                                onDurationSelected(option)
                            }
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ){

            IconButton(
                onClick = {
                    onFilterIconClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter Icon"
                )
            }
        }
    }
}

@Composable
fun DateFilterWithIcon2(
    onFilterIconClick: () -> Unit,
    ){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Activity",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Surface(
            modifier = Modifier,
            tonalElevation = 2.dp,
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        )
        {

            IconButton(
                onClick = {
                    onFilterIconClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter Icon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
@Composable
fun DateFilterWithIconPreview(

){
    DateFilterWithIcon2(
        onFilterIconClick = {}
    )
}


