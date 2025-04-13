package com.example.financetracker.main_page_feature.view_records.transactions.presentation

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions

data class ViewTransactionsStates(
    val transactionsList: List<Transactions> = emptyList(),
    val durationRange: List<String> = listOf("Today","This Month","Last Month","Last 3 Months","Custom Range"),
    val selectedDuration: String = durationRange[0],
    val rangeDropDownExpanded: Boolean = false,
    val filterBottomSheetState: Boolean = false,
    val customDateAlertBoxState: Boolean = false,
    val fromDate: Long = 0,
    val toDate: Long = 0

)
