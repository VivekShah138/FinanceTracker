package com.example.financetracker.presentation.features.view_records_feature.states

import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.utils.DurationFilter
import com.example.financetracker.utils.TransactionFilter
import com.example.financetracker.utils.TransactionOrder
import com.example.financetracker.utils.TransactionTypeFilter
import com.example.financetracker.domain.model.Currency

data class ViewTransactionsStates(

    // Transaction List
    val transactionsList: List<Transactions> = emptyList(),
    val totalAmount: Double = 0.0,
    val currencySymbol: String = "$",
    val baseCurrency: Map<String, Currency> = emptyMap(),

    // Duration
    val durationRange: List<DurationFilter> = listOf(
        DurationFilter.Today,
        DurationFilter.ThisMonth,
        DurationFilter.LastMonth,
        DurationFilter.Last3Months,
        DurationFilter.CustomRange(0L,0L)
    ),

    val selectedDuration: DurationFilter = DurationFilter.Today,
    val rangeDropDownExpanded: Boolean = false,
//    val customDateAlertBoxState: Boolean = false,
    val customDeleteAlertBoxState: Boolean = false,
    val fromDate: Long = 0,
    val toDate: Long = 0,

    val filters: List<TransactionFilter> = listOf(
        TransactionFilter.TransactionType(TransactionTypeFilter.Both), // Default transaction type
        TransactionFilter.Order(TransactionOrder.Descending), // Default to Ascending order
        TransactionFilter.Category(emptyList()), // Default to all categories (empty list means no category filter)
        TransactionFilter.Duration(DurationFilter.ThisMonth) // Default to "This Month" filter
    ),

    val categories: List<Category> = emptyList(),


    // Filter
    val filterBottomSheetState: Boolean = false,
    val filterApplyButton: Boolean = true,


    // selected Transaction
    val selectedTransactions: Set<Int> = emptySet(), // Store transaction IDs or whole objects
    val isSelectionMode: Boolean = false,



    )
