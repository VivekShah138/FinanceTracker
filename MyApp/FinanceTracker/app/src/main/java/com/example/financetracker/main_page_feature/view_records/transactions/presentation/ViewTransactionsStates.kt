package com.example.financetracker.main_page_feature.view_records.transactions.presentation

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter

data class ViewTransactionsStates(

    // Transaction List
    val transactionsList: List<Transactions> = emptyList(),

    // Duration
//    val durationRange: List<String> = listOf("Today","This Month","Last Month","Last 3 Months","Custom Range"),
    val durationRange: List<DurationFilter> = listOf(
        DurationFilter.Today,
        DurationFilter.ThisMonth,
        DurationFilter.LastMonth,
        DurationFilter.Last3Months,
        DurationFilter.CustomRange(0L,0L)
    ),
//    val selectedDuration: String = durationRange[0],
    val selectedDuration: DurationFilter = DurationFilter.Today,
    val rangeDropDownExpanded: Boolean = false,
    val customDateAlertBoxState: Boolean = false,
    val fromDate: Long = 0,
    val toDate: Long = 0,

    // Filter
    val filterBottomSheetState: Boolean = false,
    val sortOrder: String = "Ascending",

    // selected Transaction
    val selectedTransactions: Set<Int> = emptySet(), // Store transaction IDs or whole objects
    val isSelectionMode: Boolean = false,



)
