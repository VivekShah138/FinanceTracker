package com.example.financetracker.budget_feature.presentation

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetUseCaseWrapper: BudgetUseCaseWrapper
): ViewModel() {

    private val _budgetStates = MutableStateFlow(BudgetStates())
    val budgetStates : StateFlow<BudgetStates> = _budgetStates.asStateFlow()

    private val budgetValidationEventChannel = Channel<AddTransactionValidationEvent>()
    val budgetValidationEvents = budgetValidationEventChannel.receiveAsFlow()

    private val uid = budgetUseCaseWrapper.getUIDLocally() ?: "Unknown"
    private val calendar = Calendar.getInstance()
    private val month = calendar.get(Calendar.MONTH) + 1 // Months are 0-based
    private val year = calendar.get(Calendar.YEAR)
    private val updatedAt: Long = System.currentTimeMillis() // for timestamps

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
            is BudgetEvents.ChangeBudgetEditState -> {
                _budgetStates.value = budgetStates.value.copy(
                    budgetEditState = budgetEvents.state
                )
            }

            BudgetEvents.SaveBudget -> {
                addBudget()
            }
        }
    }

    private fun addBudget(){
        viewModelScope.launch(Dispatchers.IO) {
            if(budgetStates.value.budget.isEmpty()){
                budgetValidationEventChannel.send(AddTransactionValidationEvent.Failure(errorMessage = "Field can not be empty"))
                return@launch
            }

            val existing = budgetUseCaseWrapper.getBudgetLocalUseCase(uid, month, year)

            budgetUseCaseWrapper.insertBudgetLocalUseCase(
                Budget(
                    id = existing?.id ?: UUID.randomUUID().toString(),
                    userId = uid,
                    amount = _budgetStates.value.budget.toDoubleOrNull() ?: 0.0,
                    month = month,
                    year = year,
                    updatedAt = updatedAt
                )
            )


            budgetUseCaseWrapper.insertBudgetLocalUseCase(
                Budget(
                    id = existing?.id ?: UUID.randomUUID().toString(),
                    userId = uid,
                    amount = _budgetStates.value.budget.toDoubleOrNull() ?: 0.0,
                    month = month,
                    year = year,
                    updatedAt = updatedAt
                )
            )
            budgetValidationEventChannel.send(AddTransactionValidationEvent.Success)
        }
    }

    private fun getBudget(){
        viewModelScope.launch(Dispatchers.IO) {
            val existing = budgetUseCaseWrapper.getBudgetLocalUseCase(uid, month, year)
            val userProfile = budgetUseCaseWrapper.getUserProfileFromLocalDb(uid = uid)
            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"

            _budgetStates.value = budgetStates.value.copy(
                budget = existing?.amount.toString() ?: "",
                budgetCurrencySymbol = baseCurrencySymbol
            )
        }
    }
}