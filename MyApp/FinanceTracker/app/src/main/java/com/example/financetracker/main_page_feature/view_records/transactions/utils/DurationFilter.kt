package com.example.financetracker.main_page_feature.view_records.transactions.utils


sealed class DurationFilter(val label: String) {
    data object Today : DurationFilter("Today")
    data object ThisMonth : DurationFilter("This Month")
    data object LastMonth : DurationFilter("Last Month")
    data object Last3Months : DurationFilter("Last 3 Months")
    data class CustomRange(val from: Long, val to: Long) : DurationFilter("Custom Range")
}
