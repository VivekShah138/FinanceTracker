package com.example.financetracker.main_page_feature.charts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.charts.domain.usecases.ChartsUseCaseWrapper
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionOrder
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionTypeFilter
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

    private val userId = chartsUseCaseWrapper.getUIDLocally()


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
//
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
            Log.d("ChartsViewModel","userId $userId")
            if (userId == null) {
                Log.e("ChartsViewModel", "UserId is null! Cannot fetch categories.")
                return@launch
            }

            val expense = chartsUseCaseWrapper.getAllCategories("expense", userId).first()
            val income = chartsUseCaseWrapper.getAllCategories("income", userId).first()
            val allCategories = expense + income

            val (fromDate, toDate) = if (showOnlyYear) {
                getYearRangeInMillis(onlyYear)
            } else {
                getDateRangeInMillis(year, month)
            }

            val toDateFormatted = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(toDate))
            val fromDateFormatted = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(fromDate))



            Log.d("ChartsViewModelE","To Date: $toDate  From Date: $fromDate")
            Log.d("ChartsViewModelE","To Date Formatted: $toDateFormatted")
            Log.d("ChartsViewModelE","From Date Formatted: $fromDateFormatted")
            Log.d("ChartsViewModelE","showOnlyYear: $showOnlyYear")

            val allTransactions = chartsUseCaseWrapper.getAllTransactionsFilters(
                uid = userId,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both),
                    TransactionFilter.Order(TransactionOrder.Ascending),
                    TransactionFilter.Category(allCategories),
                    TransactionFilter.Duration(DurationFilter.CustomRange(from = fromDate, to = toDate))
                )
            ).first()

            Log.d("ChartsViewModel","allTransactionsFilter $allTransactions")

            val incomeDataWithCategory = _chartStates.value.incomeDataWithCategory.toMutableMap().apply { clear() }
            val expenseDataWithCategory = _chartStates.value.expenseDataWithCategory.toMutableMap().apply { clear() }

            allTransactions.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        val currentIncomeAmount = incomeDataWithCategory[transaction.category] ?: 0.0
                        incomeDataWithCategory[transaction.category] = currentIncomeAmount + transaction.amount

                        Log.d("ChartsViewModel", "income Amount for ${transaction.category}: ${incomeDataWithCategory[transaction.category]}")
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        val currentExpenseAmount = expenseDataWithCategory[transaction.category] ?: 0.0
                        expenseDataWithCategory[transaction.category] = currentExpenseAmount + transaction.amount

                        Log.d("ChartsViewModel", "expense Amount for ${transaction.category}: ${expenseDataWithCategory[transaction.category]}")
                    }
                }
            }

            _chartStates.value = _chartStates.value.copy(
                incomeDataWithCategory = incomeDataWithCategory,
                expenseDataWithCategory = expenseDataWithCategory
            )

            Log.d("ChartsViewModel","incomeDataWithCategory: ${_chartStates.value.incomeDataWithCategory}")
            Log.d("ChartsViewModel","expenseDataWithCategory: ${_chartStates.value.expenseDataWithCategory}")

            val userProfile = chartsUseCaseWrapper.getUserProfileFromLocalDb(userId)
            Log.d("ChartsViewModel","userProfile: ${userProfile}")
            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"
            Log.d("ChartsViewModel","baseCurrencySymbol: $baseCurrencySymbol")

            _chartStates.value = _chartStates.value.copy(
                baseCurrencySymbol = baseCurrencySymbol
            )

        }
    }



    private fun getIncomeAndExpenseAmountYearly(onlyYear: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ChartsViewModel","userId $userId")
            if (userId == null) {
                Log.e("ChartsViewModel", "UserId is null! Cannot fetch categories.")
                return@launch
            }
            val expense = chartsUseCaseWrapper.getAllCategories("expense", userId).first()
            val income = chartsUseCaseWrapper.getAllCategories("income", userId).first()
            val allCategories = expense + income

            val (fromDateYear, toDateYear) = getYearRangeInMillis(year = onlyYear)

            val allTransactionsThisMonth = chartsUseCaseWrapper.getAllTransactionsFilters(
                uid = userId,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both), // Default transaction type
                    TransactionFilter.Order(TransactionOrder.Ascending), // Default to Ascending order
                    TransactionFilter.Category(allCategories), // Default to all categories (empty list means no category filter)
//                    TransactionFilter.Duration(DurationFilter.ThisMonth) // Default to "This Month" filter
                    TransactionFilter.Duration(DurationFilter.CustomRange(from = fromDateYear, to = toDateYear))
                )
            ).first()
            Log.d("ChartsViewModel","allTransactionsFilter $allTransactionsThisMonth")


            val incomeDataWithCategory = _chartStates.value.incomeDataWithCategory.toMutableMap()
            val expenseDataWithCategory = _chartStates.value.expenseDataWithCategory.toMutableMap()

            allTransactionsThisMonth.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        val currentIncomeAmount = incomeDataWithCategory[transaction.category] ?: 0.0
                        incomeDataWithCategory[transaction.category] = currentIncomeAmount + transaction.amount

                        Log.d("ChartsViewModel", "income Amount This Month for ${transaction.category}: ${incomeDataWithCategory[transaction.category]}")
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        val currentExpenseAmount = expenseDataWithCategory[transaction.category] ?: 0.0
                        expenseDataWithCategory[transaction.category] = currentExpenseAmount + transaction.amount

                        Log.d("ChartsViewModel", "expense Amount This Month for ${transaction.category}: ${expenseDataWithCategory[transaction.category]}")
                    }
                }
            }

            _chartStates.value = chartStates.value.copy(
                incomeDataWithCategory = incomeDataWithCategory,
                expenseDataWithCategory = expenseDataWithCategory
            )

            Log.d("ChartsViewModel","incomeDataWithCategory: ${_chartStates.value.incomeDataWithCategory}")
            Log.d("ChartsViewModel","expenseDataWithCategory: ${_chartStates.value.expenseDataWithCategory}")
        }
    }

    private fun getIncomeAndExpenseAmountMonthly(year: Int, month: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ChartsViewModel","userId $userId")
            if (userId == null) {
                Log.e("ChartsViewModel", "UserId is null! Cannot fetch categories.")
                return@launch
            }
            val expense = chartsUseCaseWrapper.getAllCategories("expense", userId).first()
            val income = chartsUseCaseWrapper.getAllCategories("income", userId).first()
            val allCategories = expense + income

            val (fromDateMonthYear, toDateMonthYear) = getDateRangeInMillis(year = year, month = month)

            val allTransactionsThisMonth = chartsUseCaseWrapper.getAllTransactionsFilters(
                uid = userId,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both), // Default transaction type
                    TransactionFilter.Order(TransactionOrder.Ascending), // Default to Ascending order
                    TransactionFilter.Category(allCategories), // Default to all categories (empty list means no category filter)
//                    TransactionFilter.Duration(DurationFilter.ThisMonth) // Default to "This Month" filter
                    TransactionFilter.Duration(DurationFilter.CustomRange(from = fromDateMonthYear, to = toDateMonthYear))
                )
            ).first()
            Log.d("ChartsViewModel","allTransactionsFilter $allTransactionsThisMonth")


            val incomeDataWithCategory = _chartStates.value.incomeDataWithCategory.toMutableMap()
            val expenseDataWithCategory = _chartStates.value.expenseDataWithCategory.toMutableMap()

            allTransactionsThisMonth.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        val currentIncomeAmount = incomeDataWithCategory[transaction.category] ?: 0.0
                        incomeDataWithCategory[transaction.category] = currentIncomeAmount + transaction.amount

                        Log.d("ChartsViewModel", "income Amount This Month for ${transaction.category}: ${incomeDataWithCategory[transaction.category]}")
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        val currentExpenseAmount = expenseDataWithCategory[transaction.category] ?: 0.0
                        expenseDataWithCategory[transaction.category] = currentExpenseAmount + transaction.amount

                        Log.d("ChartsViewModel", "expense Amount This Month for ${transaction.category}: ${expenseDataWithCategory[transaction.category]}")
                    }
                }
            }

            _chartStates.value = chartStates.value.copy(
                incomeDataWithCategory = incomeDataWithCategory,
                expenseDataWithCategory = expenseDataWithCategory
            )

            Log.d("ChartsViewModel","incomeDataWithCategory: ${_chartStates.value.incomeDataWithCategory}")
            Log.d("ChartsViewModel","expenseDataWithCategory: ${_chartStates.value.expenseDataWithCategory}")
        }
    }



    private fun getDateRangeInMillis(year: Int, month: Int): Pair<Long, Long> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month )  // Calendar.MONTH is 0-based
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val fromDate = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1) // move to next month
        calendar.add(Calendar.MILLISECOND, -1) // subtract 1 ms to get last moment of current month

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

        // Move to first day of next year, then subtract 1 ms
        calendar.add(Calendar.YEAR, 1)
        calendar.add(Calendar.MILLISECOND, -1)

        val toDate = calendar.timeInMillis

        return fromDate to toDate
    }

}