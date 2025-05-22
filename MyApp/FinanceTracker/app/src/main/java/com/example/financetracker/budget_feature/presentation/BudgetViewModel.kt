package com.example.financetracker.budget_feature.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.presentation.AddTransactionViewModel.AddTransactionValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetUseCaseWrapper: BudgetUseCaseWrapper
): ViewModel() {

    private val _budgetStates = MutableStateFlow(
        BudgetStates(
            selectedYear = Calendar.getInstance().get(Calendar.YEAR),
            selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
        )
    )

    val budgetStates : StateFlow<BudgetStates> = _budgetStates.asStateFlow()

    private val _singleBudgetState = MutableStateFlow<Budget?>(null)
    val singleBudgetState : StateFlow<Budget?> = _singleBudgetState.asStateFlow()

    private val budgetValidationEventChannel = Channel<AddTransactionValidationEvent>()
    val budgetValidationEvents = budgetValidationEventChannel.receiveAsFlow()

    private val uid = budgetUseCaseWrapper.getUIDLocally() ?: "Unknown"
    private val updatedAt: Long = System.currentTimeMillis()

    init {
        getBudget()
    }

    fun onEvent(budgetEvents: BudgetEvents){
        when(budgetEvents){
            is BudgetEvents.ChangeBudget -> {
                _budgetStates.value = budgetStates.value.copy(
                    budget = budgetEvents.budget
                )
            }
            is BudgetEvents.ChangeCreateBudgetState -> {
                _budgetStates.value = budgetStates.value.copy(
                    createBudgetState = budgetEvents.state
                )
            }

            BudgetEvents.SaveBudget -> {
                addBudget()
            }

            is BudgetEvents.MonthSelected -> {
                _budgetStates.value = budgetStates.value.copy(
                    selectedYear = budgetEvents.year,
                    selectedMonth = budgetEvents.month
                )

                val current = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _budgetStates.value.selectedYear)
                    set(Calendar.MONTH, _budgetStates.value.selectedMonth)
                    set(Calendar.DAY_OF_MONTH, 1)
                }

                // Update visibility logic after NextMonthClicked
                _budgetStates.value = _budgetStates.value.copy(
                    nextMonthVisibility = (current.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)) ||
                            (current.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                                    current.get(Calendar.MONTH) < Calendar.getInstance().get(Calendar.MONTH))
                )

                getBudget()

            }
            is BudgetEvents.NextMonthClicked -> {

                val current = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _budgetStates.value.selectedYear)
                    set(Calendar.MONTH, _budgetStates.value.selectedMonth)
                    set(Calendar.DAY_OF_MONTH, 1)
                }

                // Only increment if current < today (in year and month)
                if (current.before(Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_MONTH, 1) // Only compare month/year
                    })) {
                    current.add(Calendar.MONTH, 1)

                    _budgetStates.value = _budgetStates.value.copy(
                        selectedYear = current.get(Calendar.YEAR),
                        selectedMonth = current.get(Calendar.MONTH),
                    )
                }

                // Update visibility logic after NextMonthClicked
                _budgetStates.value = _budgetStates.value.copy(
                    nextMonthVisibility = (current.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)) ||
                            (current.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                                    current.get(Calendar.MONTH) < Calendar.getInstance().get(Calendar.MONTH))
                )

                getBudget()

            }
            is BudgetEvents.PreviousMonthClicked -> {

                val cal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, _budgetStates.value.selectedYear)
                    set(Calendar.MONTH, _budgetStates.value.selectedMonth)
                    add(Calendar.MONTH, -1)
                }
                _budgetStates.value = budgetStates.value.copy(
                    selectedYear = cal.get(Calendar.YEAR),
                    selectedMonth = cal.get(Calendar.MONTH),
                    nextMonthVisibility = true
                )

                getBudget()

            }

            is BudgetEvents.ChangeAlertThresholdAmount -> {

                _budgetStates.value = budgetStates.value.copy(
                    alertThresholdPercent = budgetEvents.amount
                )

            }
            is BudgetEvents.ChangeReceiveBudgetAlerts -> {

                _budgetStates.value = budgetStates.value.copy(
                    receiveAlerts = budgetEvents.state
                )
            }
        }
    }

    private fun addBudget(){
        viewModelScope.launch(Dispatchers.IO) {
            if(budgetStates.value.budget.isEmpty()){
                budgetValidationEventChannel.send(AddTransactionValidationEvent.Failure(errorMessage = "Field can not be empty"))
                return@launch
            }

            val existing = budgetUseCaseWrapper.getBudgetLocalUseCase(
                userId = uid,
                month = _budgetStates.value.selectedMonth,
                year = _budgetStates.value.selectedYear
            )

            budgetUseCaseWrapper.insertBudgetLocalUseCase(
                Budget(
                    id = existing?.id ?: UUID.randomUUID().toString(),
                    userId = uid,
                    amount = _budgetStates.value.budget.toDoubleOrNull() ?: 0.0,
                    month = _budgetStates.value.selectedMonth,
                    year = _budgetStates.value.selectedYear,
                    updatedAt = updatedAt,
                    cloudSync = false,
                    receiveAlerts = _budgetStates.value.receiveAlerts,
                    thresholdAmount = _budgetStates.value.alertThresholdPercent
                )
            )
            budgetValidationEventChannel.send(AddTransactionValidationEvent.Success)
        }
    }

    private fun getBudget(){
        viewModelScope.launch(Dispatchers.IO) {

            val existing = budgetUseCaseWrapper.getBudgetLocalUseCase(
                userId = uid,
                month = _budgetStates.value.selectedMonth,
                year = _budgetStates.value.selectedYear
            )
            val userProfile = budgetUseCaseWrapper.getUserProfileFromLocalDb(uid = uid)
            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"


            _singleBudgetState.value = existing
            if(existing == null){
                _budgetStates.value = budgetStates.value.copy(
                    createBudgetState = true,
                    budget = "0.0"
                )
            }else {
                _budgetStates.value = budgetStates.value.copy(
                    budget = existing.amount.toString(),
                    budgetCurrencySymbol = baseCurrencySymbol,
                    createBudgetState = false,
                    receiveAlerts = existing.receiveAlerts,
                    alertThresholdPercent = existing.thresholdAmount
                )
            }
        }
    }
}