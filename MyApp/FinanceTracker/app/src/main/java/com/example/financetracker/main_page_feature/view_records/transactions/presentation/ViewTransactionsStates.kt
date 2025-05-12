package com.example.financetracker.main_page_feature.view_records.transactions.presentation

import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionOrder
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionTypeFilter
import com.example.financetracker.setup_account.domain.model.Currency

data class ViewTransactionsStates(

    // Transaction List
    val transactionsList: List<Transactions> = emptyList(),
    val totalAmount: Double = 0.0,
    val currencySymbol: String = "$",
    val baseCurrency: Map<String,Currency> = emptyMap(),

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
