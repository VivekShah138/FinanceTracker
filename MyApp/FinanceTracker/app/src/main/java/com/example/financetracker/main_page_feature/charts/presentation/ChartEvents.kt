package com.example.financetracker.main_page_feature.charts.presentation

sealed class ChartEvents{

    data class ChangeType(val type: String,val state: Boolean): ChartEvents()
    data object PreviousMonthClicked : ChartEvents()
    data object NextMonthClicked : ChartEvents()
    data class MonthSelected(val year: Int, val month: Int) : ChartEvents()
    data object PreviousYearClicked : ChartEvents()
    data object NextYearClicked : ChartEvents()
    data class YearSelected(val year: Int) : ChartEvents()
    data class ChangeDateToYear(val state: Boolean): ChartEvents()

}
