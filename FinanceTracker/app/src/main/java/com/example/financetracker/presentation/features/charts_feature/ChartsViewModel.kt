package com.example.financetracker.presentation.features.charts_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.ChartsUseCaseWrapper
import com.example.financetracker.utils.DurationFilter
import com.example.financetracker.utils.TransactionFilter
import com.example.financetracker.utils.TransactionOrder
import com.example.financetracker.utils.TransactionTypeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val chartsUseCaseWrapper: ChartsUseCaseWrapper
): ViewModel() {

    private val _chartStates = MutableStateFlow(
        ChartStates(
            selectedYear = Calendar.getInstance().get(Calendar.YEAR),
            selectedMonth = Calendar.getInstance().get(Calendar.MONTH),
            selectedOnlyYear = Calendar.getInstance().get(Calendar.YEAR),
        )
    )
    val chartStates : StateFlow<ChartStates> = _chartStates.asStateFlow()

    private val userId = chartsUseCaseWrapper.getUIDLocalUseCase()


    init {
        getIncomeAndExpenseAmount(
            showOnlyYear = _chartStates.value.showOnlyYear,
            year = _chartStates.value.selectedYear,
            month = _chartStates.value.selectedMonth,
            onlyYear = _chartStates.value.selectedOnlyYear,
        )
    }

    fun onEvent(chartEvents: ChartEvents){
        when(chartEvents){
            is ChartEvents.ChangeType -> {
                _chartStates.value = chartStates.value.copy(
                    transactionType = chartEvents.type,
                    typeDropDown = chartEvents.state
                )
            }
            is ChartEvents.MonthSelected -> {
                _chartStates.value = chartStates.value.copy(
                    selectedYear = chartEvents.year,
                    selectedMonth = chartEvents.month
                )

                val current = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _chartStates.value.selectedYear)
                    set(Calendar.MONTH, _chartStates.value.selectedMonth)
                    set(Calendar.DAY_OF_MONTH, 1)
                }

                // Update visibility logic after NextMonthClicked
                _chartStates.value = _chartStates.value.copy(
                    nextMonthVisibility = (current.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)) ||
                            (current.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                                    current.get(Calendar.MONTH) < Calendar.getInstance().get(Calendar.MONTH))
                )

                getIncomeAndExpenseAmount(
                    showOnlyYear = _chartStates.value.showOnlyYear,
                    year = _chartStates.value.selectedYear,
                    month = _chartStates.value.selectedMonth,
                    onlyYear = _chartStates.value.selectedOnlyYear,
                )

            }
            is ChartEvents.NextMonthClicked -> {

                val current = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _chartStates.value.selectedYear)
                    set(Calendar.MONTH, _chartStates.value.selectedMonth)
                    set(Calendar.DAY_OF_MONTH, 1)
                }

                // Only increment if current < today (in year and month)
                if (current.before(Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_MONTH, 1) // Only compare month/year
                    })) {
                    current.add(Calendar.MONTH, 1)

                    _chartStates.value = chartStates.value.copy(
                        selectedYear = current.get(Calendar.YEAR),
                        selectedMonth = current.get(Calendar.MONTH),
                    )
                }

                // Update visibility logic after NextMonthClicked
                _chartStates.value = chartStates.value.copy(
                    nextMonthVisibility = (current.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)) ||
                            (current.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                                    current.get(Calendar.MONTH) < Calendar.getInstance().get(Calendar.MONTH))
                )

                getIncomeAndExpenseAmount(
                    showOnlyYear = _chartStates.value.showOnlyYear,
                    year = _chartStates.value.selectedYear,
                    month = _chartStates.value.selectedMonth,
                    onlyYear = _chartStates.value.selectedOnlyYear,
                )


            }
            is ChartEvents.PreviousMonthClicked -> {

                val cal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _chartStates.value.selectedYear)
                    set(Calendar.MONTH, _chartStates.value.selectedMonth)
                    add(Calendar.MONTH, -1)
                }
                _chartStates.value = chartStates.value.copy(
                    selectedYear = cal.get(Calendar.YEAR),
                    selectedMonth = cal.get(Calendar.MONTH),
                    nextMonthVisibility = true
                )

                getIncomeAndExpenseAmount(
                    showOnlyYear = _chartStates.value.showOnlyYear,
                    year = _chartStates.value.selectedYear,
                    month = _chartStates.value.selectedMonth,
                    onlyYear = _chartStates.value.selectedOnlyYear,
                )

            }

            ChartEvents.NextYearClicked -> {

                val current = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _chartStates.value.selectedOnlyYear)
                    set(Calendar.MONTH, 0)
                    set(Calendar.DAY_OF_MONTH, 1)
                }

                // Only increment if current < today (in year and month)
                if (current.before(Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_MONTH, 1) // Only compare month/year
                    })) {
                    current.add(Calendar.YEAR, 1)

                    _chartStates.value = chartStates.value.copy(
                        selectedOnlyYear = current.get(Calendar.YEAR),

                    )
                }

                // Update visibility logic after NextMonthClicked
                _chartStates.value = chartStates.value.copy(
                    nextMonthVisibility = (current.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)))

                getIncomeAndExpenseAmount(
                    showOnlyYear = _chartStates.value.showOnlyYear,
                    year = _chartStates.value.selectedYear,
                    month = _chartStates.value.selectedMonth,
                    onlyYear = _chartStates.value.selectedOnlyYear,
                )

            }
            ChartEvents.PreviousYearClicked -> {


                val cal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _chartStates.value.selectedOnlyYear)
                    set(Calendar.MONTH, 0)
                    add(Calendar.YEAR, -1)
                }
                _chartStates.value = chartStates.value.copy(
                    selectedOnlyYear = cal.get(Calendar.YEAR),
                    nextMonthVisibility = true
                )

                getIncomeAndExpenseAmount(
                    showOnlyYear = _chartStates.value.showOnlyYear,
                    year = _chartStates.value.selectedYear,
                    month = _chartStates.value.selectedMonth,
                    onlyYear = _chartStates.value.selectedOnlyYear,
                )
            }
            is ChartEvents.YearSelected -> {
                _chartStates.value = chartStates.value.copy(
                    selectedOnlyYear = chartEvents.year
                )

                _chartStates.value = chartStates.value.copy(
                    nextMonthVisibility = (chartEvents.year < Calendar.getInstance().get(Calendar.YEAR)))

                getIncomeAndExpenseAmount(
                    showOnlyYear = _chartStates.value.showOnlyYear,
                    year = _chartStates.value.selectedYear,
                    month = _chartStates.value.selectedMonth,
                    onlyYear = _chartStates.value.selectedOnlyYear,
                )
            }
            is ChartEvents.ChangeDateToYear -> {

                _chartStates.value = chartStates.value.copy(
                    showOnlyYear = chartEvents.state,
                )

                if(_chartStates.value.showOnlyYear){
                    checkYearVisibility()
                    _chartStates.value = chartStates.value.copy(
                        selectedYear = Calendar.getInstance().get(Calendar.YEAR),
                        selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
                    )
                } else {
                    checkMonthYearVisibility()
                    _chartStates.value = chartStates.value.copy(
                        selectedOnlyYear = Calendar.getInstance().get(Calendar.YEAR),
                    )
                }

                getIncomeAndExpenseAmount(
                    showOnlyYear = _chartStates.value.showOnlyYear,
                    year = _chartStates.value.selectedYear,
                    month = _chartStates.value.selectedMonth,
                    onlyYear = _chartStates.value.selectedOnlyYear,
                )
            }
        }
    }

    private fun checkYearVisibility(){
        val current = Calendar.getInstance().apply {
            set(Calendar.YEAR, _chartStates.value.selectedOnlyYear)
            set(Calendar.MONTH, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        _chartStates.value = chartStates.value.copy(
            nextMonthVisibility = (current.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)))
    }

    private fun checkMonthYearVisibility(){
        val current = Calendar.getInstance().apply {
            set(Calendar.YEAR, _chartStates.value.selectedYear)
            set(Calendar.MONTH, _chartStates.value.selectedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        // Update visibility logic after NextMonthClicked
        _chartStates.value = chartStates.value.copy(
            nextMonthVisibility = (current.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)) ||
                    (current.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                            current.get(Calendar.MONTH) < Calendar.getInstance().get(Calendar.MONTH))
        )
    }


    private fun getIncomeAndExpenseAmount(showOnlyYear: Boolean, year: Int, month: Int,onlyYear: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount userId $userId")

            if (userId == null) {
                Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount UserId is null! Cannot fetch categories.")
                return@launch
            }

            val expense = chartsUseCaseWrapper.getAllCategoriesLocalUseCase("expense", userId).first()
            val income = chartsUseCaseWrapper.getAllCategoriesLocalUseCase("income", userId).first()
            val allCategories = expense + income

            val (fromDate, toDate) = if (showOnlyYear) {
                getYearRangeInMillis(onlyYear)
            } else {
                getDateRangeInMillis(year, month)
            }

            val toDateFormatted = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(toDate))
            val fromDateFormatted = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(fromDate))


            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount To Date: $toDate  From Date: $fromDate")
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount To Date Formatted: $toDateFormatted")
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount From Date Formatted: $fromDateFormatted")
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount showOnlyYear: $showOnlyYear")


            val allTransactions = chartsUseCaseWrapper.getAllTransactionsFilters(
                uid = userId,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both),
                    TransactionFilter.Order(TransactionOrder.Ascending),
                    TransactionFilter.Category(allCategories),
                    TransactionFilter.Duration(DurationFilter.CustomRange(from = fromDate, to = toDate))
                )
            ).first()

            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount showOnlyYear: $showOnlyYear")
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount allTransactionsFilter $allTransactions")

            val incomeDataWithCategory = _chartStates.value.incomeDataWithCategory.toMutableMap().apply { clear() }
            val expenseDataWithCategory = _chartStates.value.expenseDataWithCategory.toMutableMap().apply { clear() }

            allTransactions.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        val currentIncomeAmount = incomeDataWithCategory[transaction.category] ?: 0.0
                        incomeDataWithCategory[transaction.category] = currentIncomeAmount + transaction.amount
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        val currentExpenseAmount = expenseDataWithCategory[transaction.category] ?: 0.0
                        expenseDataWithCategory[transaction.category] = currentExpenseAmount + transaction.amount
                    }
                }
            }

            _chartStates.value = _chartStates.value.copy(
                incomeDataWithCategory = incomeDataWithCategory,
                expenseDataWithCategory = expenseDataWithCategory
            )

            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount incomeDataWithCategory: ${_chartStates.value.incomeDataWithCategory}")
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount expenseDataWithCategory: ${_chartStates.value.expenseDataWithCategory}")

            val userProfile = chartsUseCaseWrapper.getUserProfileFromLocalUseCase(userId)
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount userProfile: ${userProfile}")

            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"
            Logger.d(Logger.Tag.CHARTS_VIEWMODEL,"getIncomeAndExpenseAmount baseCurrencySymbol: $baseCurrencySymbol")

            _chartStates.value = _chartStates.value.copy(
                baseCurrencySymbol = baseCurrencySymbol
            )

        }
    }



    private fun getDateRangeInMillis(year: Int, month: Int): Pair<Long, Long> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month )
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val fromDate = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)

        val toDate = calendar.timeInMillis

        return fromDate to toDate
    }

    private fun getYearRangeInMillis(year: Int): Pair<Long, Long> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val fromDate = calendar.timeInMillis

        calendar.add(Calendar.YEAR, 1)
        calendar.add(Calendar.MILLISECOND, -1)

        val toDate = calendar.timeInMillis

        return fromDate to toDate
    }

}