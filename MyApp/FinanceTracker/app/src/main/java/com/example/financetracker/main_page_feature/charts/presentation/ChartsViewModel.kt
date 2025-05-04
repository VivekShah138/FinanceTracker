package com.example.financetracker.main_page_feature.charts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import com.example.financetracker.budget_feature.presentation.BudgetStates
import com.example.financetracker.main_page_feature.charts.domain.usecases.ChartsUseCaseWrapper
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionOrder
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionTypeFilter
import com.patrykandpatrick.vico.core.chart.Chart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.reflect.typeOf


@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val chartsUseCaseWrapper: ChartsUseCaseWrapper
): ViewModel() {

    private val _chartStates = MutableStateFlow(
        ChartStates(
            selectedYear = Calendar.getInstance().get(Calendar.YEAR),
            selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
        )
    )
    val chartStates : StateFlow<ChartStates> = _chartStates.asStateFlow()

    private val userId = chartsUseCaseWrapper.getUIDLocally()


    init {
        getIncomeAndExpenseAmount()
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

                getIncomeAndExpenseAmount()


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

                getIncomeAndExpenseAmount()

            }
        }
    }


    private fun getIncomeAndExpenseAmount() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ChartsViewModel","userId $userId")
            if (userId == null) {
                Log.e("ChartsViewModel", "UserId is null! Cannot fetch categories.")
                return@launch
            }
            val expense = chartsUseCaseWrapper.getAllCategories("expense", userId).first()
            val income = chartsUseCaseWrapper.getAllCategories("income", userId).first()
            val allCategories = expense + income

            val allTransactionsThisMonth = chartsUseCaseWrapper.getAllTransactionsFilters(
                uid = userId,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both), // Default transaction type
                    TransactionFilter.Order(TransactionOrder.Ascending), // Default to Ascending order
                    TransactionFilter.Category(allCategories), // Default to all categories (empty list means no category filter)
                    TransactionFilter.Duration(DurationFilter.ThisMonth) // Default to "This Month" filter
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
}