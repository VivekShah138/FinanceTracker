package com.example.financetracker.main_page_feature.charts.presentation

sealed class ChartEvents{

    data class ChangeType(val type: String,val state: Boolean): ChartEvents()
    data object PreviousMonthClicked : ChartEvents()
    data object NextMonthClicked : ChartEvents()
    data class MonthSelected(val year: Int, val month: Int) : ChartEvents()

}
