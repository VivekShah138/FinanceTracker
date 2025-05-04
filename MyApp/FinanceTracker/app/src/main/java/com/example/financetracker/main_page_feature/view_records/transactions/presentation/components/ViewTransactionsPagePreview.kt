package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.components.TransactionItemCard
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ViewTransactionsPagePreview(){

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

    val selectedTransactionList: Set<Int> = setOf(2,4)

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        DateFilterWithIcon(
            selectedDuration = DurationFilter.Today,
            rangeDropDownExpanded = false,
            onDurationSelected = {it},
            onFilterIconClick = {},
            filterOptions = listOf(
                DurationFilter.Today,
                DurationFilter.ThisMonth,
                DurationFilter.LastMonth,
                DurationFilter.Last3Months,
                DurationFilter.CustomRange(0L,0L)
            ),
            onRangeDropDownClick = {},
            onRangeDropDownDismiss = {}
        )

        LazyColumn {
            items(transactionList){ transaction ->

                val isSelected = selectedTransactionList.contains(transaction.transactionId)


                val priceColor = if (transaction.transactionType == "Expense") {
                    Color.Red
                } else {
                    Color.Green
                }

                TransactionItemCard(
                    item = transaction,
                    onClick = {

                    },
                    onLongClick = {

                    },
                    isSelected = isSelected,
                    isSelectionMode = true
                )
            }
        }
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
//    val filterOptions = listOf("This Month", "Last Month", "Last 3 Months", "Custom Date")
//    var rangeDropDownExpanded by remember { mutableStateOf(false) }

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

