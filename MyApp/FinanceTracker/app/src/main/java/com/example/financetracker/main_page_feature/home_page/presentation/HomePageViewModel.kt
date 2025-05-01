package com.example.financetracker.main_page_feature.home_page.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.home_page.domain.usecases.HomePageUseCaseWrapper
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionOrder
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionTypeFilter
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
//            val allTransactions = homePageUseCaseWrapper.getAllTransactions(userId).first()
            val expense = homePageUseCaseWrapper.getAllCategories("expense", userId).first()
            val income = homePageUseCaseWrapper.getAllCategories("income", userId).first()
            val allCategories = expense + income
            val allTransactionsThisMonth = homePageUseCaseWrapper.getAllTransactionsFilters(
                uid = userId,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both), // Default transaction type
                    TransactionFilter.Order(TransactionOrder.Ascending), // Default to Ascending order
                    TransactionFilter.Category(allCategories), // Default to all categories (empty list means no category filter)
                    TransactionFilter.Duration(DurationFilter.ThisMonth) // Default to "This Month" filter
                )
            ).first()
            Log.d("HomePageViewModel","allTransactionsFilter $allTransactionsThisMonth")

            val allTransactions = homePageUseCaseWrapper.getAllTransactions(uid = userId).first()
            Log.d("HomePageViewModel","allTransactions $allTransactions")

            val userProfile = homePageUseCaseWrapper.getUserProfileLocal()
            Log.d("HomePageViewModel","userProfile $userProfile")


            var incomeAmountThisMonth = 0.0
            var incomeAmountOverAll = 0.0
            var expenseAmountThisMonth = 0.0
            var expenseAmountOverAll = 0.0
            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"
            Log.d("HomePageViewModel","baseCurrency $baseCurrencySymbol")


            allTransactionsThisMonth.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        incomeAmountThisMonth += transaction.amount
                        Log.d("HomePageViewModel","income Amount This Month $incomeAmountThisMonth")
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        expenseAmountThisMonth += transaction.amount
                        Log.d("HomePageViewModel","expense Amount This Month $expenseAmountThisMonth")
                    }
                }
            }

            allTransactions.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        incomeAmountOverAll += transaction.amount
                        Log.d("HomePageViewModel","income Amount OverAll $incomeAmountOverAll")
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        expenseAmountOverAll += transaction.amount
                        Log.d("HomePageViewModel","expense Amount OverAll $expenseAmountOverAll")
                    }
                }
            }



            val formattedIncomeThisMonth = String.format(Locale.US, "%.2f", incomeAmountThisMonth)
//            val formattedIncomeOverAll = String.format(Locale.US, "%.2f", incomeAmountOverAll)
            val formattedExpenseThisMonth = String.format(Locale.US, "%.2f", expenseAmountThisMonth)
//            val formattedExpenseOverAll = String.format(Locale.US, "%.2f", expenseAmountOverAll)
            val formattedAccountBalance = String.format(Locale.US, "%.2f", (incomeAmountOverAll - expenseAmountOverAll))

            _homePageStates.value = homePageStates.value.copy(
                incomeAmount = formattedIncomeThisMonth,
                expenseAmount = formattedExpenseThisMonth,
                currencySymbol = baseCurrencySymbol,
                accountBalance =formattedAccountBalance
            )
        }
    }
}