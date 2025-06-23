package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.*
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.financetracker.core.core_presentation.components.AppTopBar
import java.text.SimpleDateFormat
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.ui.theme.AppTheme


@Preview(showBackground = true, showSystemUi = true, name = "With Exchange Rate")
@Composable
fun SingleTransactionWithExchangeRatePreview() {
    val transaction = Transactions(
        transactionType = "Expense",
        category = "Food",
        currency = emptyMap(),
        amount = 50.0,
        exchangeRate = 1.1,
        convertedAmount = 55.0,
        transactionName = "Dinner at Restaurant",
        description = "Friday night dinner with friends",
        dateTime = 11234567,
        cloudSync = true,
        userUid = "",
        isRecurring = false
    )

    AppTheme(
        dynamicColor = false
    ) {
        SingleTransactionScreen2(transaction = transaction, baseCurrency = emptyMap())
    }


}

@Preview(showBackground = true, showSystemUi = true,name = "Without Exchange Rate")
@Composable
fun SingleTransactionWithoutExchangeRatePreview() {
    val transaction = Transactions(
        transactionType = "Income",
        category = "Salary",
        currency = emptyMap(),
        amount = 3000.0,
        exchangeRate = null,
        convertedAmount = null,
        transactionName = "Monthly Salary",
        description = null,
        dateTime = 11234567,
        cloudSync = false,
        userUid = "",
        isRecurring = false
    )

    AppTheme(
        dynamicColor = false
    ) {
        SingleTransactionScreen2(transaction = transaction, baseCurrency = emptyMap())
    }


}



@Composable
fun SingleTransactionScreen2(transaction: Transactions,baseCurrency: Map<String, com.example.financetracker.setup_account.domain.model.Currency>) {

    val transactionCurrencySymbol = transaction.currency?.entries?.firstOrNull()?.value?.symbol ?: "$"
    val transactionCurrencyCode = transaction.currency?.entries?.firstOrNull()?.key ?: "USD"
    val baseCurrencySymbol = transaction.currency?.entries?.firstOrNull()?.value?.symbol ?: "£"
    val baseCurrencyCode = transaction.currency?.entries?.firstOrNull()?.key ?: "GPB"
    val amount = transaction.amount
    val transactionName = transaction.transactionName
    val formattedDateTime = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        .format(Date(transaction.dateTime))
    val transactionType = transaction.transactionType
    val transactionCategory = transaction.category
    val convertedAmount = transaction.convertedAmount
    val exchangeRate = transaction.exchangeRate
    val transactionDescription = transaction.description
    val cloudSync = transaction.cloudSync


    Scaffold(

        topBar = {
            AppTopBar(
                title = "Transaction Details",
                showBackButton = true,
                showMenu = false,
                onBackClick = {

                },
                backgroundColor = MaterialTheme.colorScheme.secondary,
                titleContentColor = MaterialTheme.colorScheme.onTertiary,
                navigationIconContentColor = MaterialTheme.colorScheme.onTertiary,
                actionIconContentColor = MaterialTheme.colorScheme.onTertiary,
            )
        },
        containerColor = Color.Transparent,


    ) {paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 1f / 3f)
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                        .background(
                            color = MaterialTheme.colorScheme.secondary
                        )

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = String.format(Locale.US, "$baseCurrencySymbol%.2f", amount),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 40.sp
                            ),
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = transactionName,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        Text(
                            text = formattedDateTime,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                }

                TransactionDetailsCard(
                    transactionType = transactionType,
                    transactionCategory = transactionCategory,
                    convertedAmount = convertedAmount,
                    transactionCurrencySymbol = transactionCurrencySymbol,
                    baseCurrencyCode = baseCurrencyCode,
                    transactionDescription = transactionDescription,
                    cloudSync = cloudSync,
                    exchangeRate = exchangeRate,
                    transactionCurrencyCode = transactionCurrencyCode,
                    screenHeight = screenHeight
                )


            }

            Button(
                onClick = { /* your action */ },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Delete")
            }
        }
    }
}


@Composable
fun TransactionDetailsCard(
    transactionType: String,
    transactionCategory: String,
    convertedAmount: Double?,
    transactionCurrencySymbol: String,
    baseCurrencyCode: String,
    transactionDescription: String?,
    cloudSync: Boolean,
    exchangeRate: Double?,
    transactionCurrencyCode: String,
    screenHeight: Dp
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight * 2f / 3f) + 50.dp)
            .offset(y = (-50).dp) // negative offset to lift it over the top box
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // Type / Category / Amount row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Type",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = transactionType,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = transactionCategory,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Log.d("ViewTransactionsViewModelS","convertedAmount $convertedAmount")
                    if (convertedAmount != null && convertedAmount != 0.0) {
                        Text(
                            text = "Amount",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = String.format("$transactionCurrencySymbol%.2f", convertedAmount),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    } else {
                        Text(
                            text = "Currency",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = baseCurrencyCode,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal, fontSize = 18.sp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // Description section
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (transactionDescription.isNullOrEmpty()) "No Description" else transactionDescription,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Spacer(Modifier.height(30.dp))


            // Cloud Sync row with status text and icon
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Cloud Sync",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Show a different icon based on cloudSync state (uploaded or not uploaded)
                    if (cloudSync) {
                        Icon(
                            imageVector = Icons.Default.CloudDone, // Cloud done icon for uploaded
                            contentDescription = "Uploaded to Cloud",
                            tint = Color(0xFF2E7D32)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Uploaded",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.CloudOff, // Cloud off icon for not uploaded
                            contentDescription = "Not Uploaded to Cloud",
                            tint = Color(0xFFD32F2F)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Not Uploaded",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            if (convertedAmount == null && convertedAmount != 0.0) {
                Text(
                    text = "Exchange Rate",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = "1 $baseCurrencyCode = $exchangeRate $transactionCurrencyCode",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}


//@Composable
//fun SingleTransactionScreen(transaction: Transactions) {
//    val currencySymbol = transaction.currency?.entries?.firstOrNull()?.value?.symbol ?: "$"
//    val formattedAmount = NumberFormat.getCurrencyInstance().apply {
//        currency = Currency.getInstance(transaction.currency?.entries?.firstOrNull()?.key ?: "USD")
//    }.format(transaction.amount)
//
//    val formattedDateTime = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
//        .format(Date(transaction.dateTime))
//
//    Card(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(20.dp),
//        elevation = CardDefaults.cardElevation(8.dp)
//    ) {
//        Column(modifier = Modifier.padding(20.dp)) {
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(
//                        modifier = Modifier
//                            .size(48.dp)
//                            .background(
//                                if (transaction.transactionType == "Income") Color(0xFF4CAF50)
//                                else Color(0xFFF44336),
//                                shape = CircleShape
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = if (transaction.transactionType == "Income") Icons.Default.ArrowDownward
//                            else Icons.Default.ArrowUpward,
//                            contentDescription = null,
//                            tint = Color.White
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(12.dp))
//                    Column {
//                        Text(
//                            text = transaction.transactionName,
//                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
//                        )
//                        Text(
//                            text = transaction.category,
//                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
//                        )
//                    }
//                }
//                Text(
//                    text = formattedAmount,
//                    style = MaterialTheme.typography.titleLarge.copy(
//                        fontWeight = FontWeight.Bold,
//                        color = if (transaction.transactionType == "Income") Color(0xFF4CAF50) else Color(0xFFF44336)
//                    ),
//                    textAlign = TextAlign.End
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Icon(imageVector = Icons.Default.AccessTime, contentDescription = null, tint = Color.Gray)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = formattedDateTime,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Icon(imageVector = Icons.Default.AttachMoney, contentDescription = null, tint = Color.Gray)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "Currency: $currencySymbol",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//
//            if (transaction.exchangeRate != null && transaction.convertedAmount != null) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Icon(imageVector = Icons.Default.SyncAlt, contentDescription = null, tint = Color.Gray)
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "Exchange: ${transaction.exchangeRate} → $currencySymbol ${transaction.convertedAmount}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//            }
//
//            if (!transaction.description.isNullOrEmpty()) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "Notes",
//                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
//                )
//                Text(
//                    text = transaction.description,
//                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 18.sp)
//                )
//            }
//        }
//    }
//}
