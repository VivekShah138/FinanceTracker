package com.example.financetracker.main_page_feature.home_page.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.home_page.domain.usecases.HomePageUseCaseWrapper
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsStates
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homePageUseCaseWrapper: HomePageUseCaseWrapper,
    private val userCasesWrapperSetupAccount: SetupAccountUseCasesWrapper
): ViewModel(){

    init {
        getUserProfile()
        updateCurrencyRatesOneTime()
        updateCurrencyRatesPeriodically()
        getIncomeAndExpenseAmount()

    }

    private val _homePageStates = MutableStateFlow(HomePageStates())
    val homePageStates : StateFlow<HomePageStates> = _homePageStates.asStateFlow()

    private val userId = homePageUseCaseWrapper.getUIDLocally() ?: "Unknown"


    fun onEvent(homePageEvents: HomePageEvents){
        when(homePageEvents){
            is HomePageEvents.Logout -> {
                viewModelScope.launch {
                    homePageUseCaseWrapper.logoutUseCase()
                }
            }
        }
    }

    fun getUserProfile(){
        viewModelScope.launch {
            val userProfile = homePageUseCaseWrapper.getUserProfileLocal()
            Log.d("HomePageViewModel","userProfile $userProfile")
        }
    }

    private fun updateCurrencyRatesOneTime(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("WorkManagerCurrencies","one time function called inside homepage")

            userCasesWrapperSetupAccount.insertCurrencyRatesLocalOneTime()
        }
    }

    private fun updateCurrencyRatesPeriodically(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("WorkManagerCurrencies","periodically time function called inside homepage")
            userCasesWrapperSetupAccount.insertCurrencyRatesLocalPeriodically()
        }
    }

    private fun getIncomeAndExpenseAmount() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("HomePageViewModel","userId $userId")
            val allTransactions = homePageUseCaseWrapper.getAllTransactions(userId).first()
            Log.d("HomePageViewModel","allTransactions $allTransactions")

            val userProfile = homePageUseCaseWrapper.getUserProfileLocal()
            Log.d("HomePageViewModel","userProfile $userProfile")


            var incomeAmount = 0.0
            var expenseAmount = 0.0
            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"
            Log.d("HomePageViewModel","baseCurrency $baseCurrencySymbol")


            allTransactions.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        incomeAmount += transaction.amount
                        Log.d("HomePageViewModel","income Amount $incomeAmount")
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        expenseAmount += transaction.amount
                        Log.d("HomePageViewModel","expense Amount $expenseAmount")
                    }
                }
            }

            val formattedIncome = String.format(Locale.US, "%.2f", incomeAmount)
            val formattedExpense = String.format(Locale.US, "%.2f", expenseAmount)

            _homePageStates.value = homePageStates.value.copy(
                incomeAmount = formattedIncome,
                expenseAmount = formattedExpense,
                incomeCurrencySymbol = baseCurrencySymbol,
                expenseCurrencySymbol = baseCurrencySymbol
            )

        }
    }
}