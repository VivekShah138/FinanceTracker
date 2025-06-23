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
import java.util.Calendar
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
            val userId2 = homePageUseCaseWrapper.getUIDLocally() ?: "Unknown"
            Log.d("HomePageViewModel","userId $userId2")

            val allTransactions = homePageUseCaseWrapper.getAllTransactions(uid = userId2).first()
            Log.d("HomePageViewModel","allTransactions $allTransactions")


            val expense = homePageUseCaseWrapper.getAllCategories("expense", userId2).first()
            val income = homePageUseCaseWrapper.getAllCategories("income", userId2).first()
            val allCategories = expense + income
            val allTransactionsThisMonth = homePageUseCaseWrapper.getAllTransactionsFilters(
                uid = userId2,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both),
                    TransactionFilter.Order(TransactionOrder.Ascending),
//                    TransactionFilter.Category(allCategories),
                    TransactionFilter.Duration(DurationFilter.ThisMonth)
                )
            ).first()






            val userProfile = homePageUseCaseWrapper.getUserProfileLocal()
            Log.d("HomePageViewModel","userProfile $userProfile")


            var incomeAmountThisMonth = 0.0
            var incomeAmountOverAll = 0.0
            var expenseAmountThisMonth = 0.0
            var expenseAmountOverAll = 0.0
            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"
            Log.d("HomePageViewModel","baseCurrency $baseCurrencySymbol")

            val incomeDataWithCategory = _homePageStates.value.incomeDataWithCategory.toMutableMap().apply { clear() }
            val expenseDataWithCategory = _homePageStates.value.expenseDataWithCategory.toMutableMap().apply { clear() }


            allTransactionsThisMonth.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        incomeAmountThisMonth += transaction.amount
                        Log.d("HomePageViewModel","income Amount This Month $incomeAmountThisMonth")

                        val currentIncomeAmount = incomeDataWithCategory[transaction.category] ?: 0.0
                        incomeDataWithCategory[transaction.category] = currentIncomeAmount + transaction.amount

                        Log.d("HomePageViewModel", "income Amount for ${transaction.category}: ${incomeDataWithCategory[transaction.category]}")

                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        expenseAmountThisMonth += transaction.amount
                        Log.d("HomePageViewModel","expense Amount This Month $expenseAmountThisMonth")

                        val currentExpenseAmount = expenseDataWithCategory[transaction.category] ?: 0.0
                        expenseDataWithCategory[transaction.category] = currentExpenseAmount + transaction.amount

                        Log.d("HomePageViewModel", "expense Amount for ${transaction.category}: ${expenseDataWithCategory[transaction.category]}")
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
            val formattedExpenseThisMonth = String.format(Locale.US, "%.2f", expenseAmountThisMonth)
            val formattedAccountBalance = String.format(Locale.US, "%.2f", (incomeAmountOverAll - expenseAmountOverAll))

            _homePageStates.value = homePageStates.value.copy(
                incomeAmount = formattedIncomeThisMonth,
                expenseAmount = formattedExpenseThisMonth,
                currencySymbol = baseCurrencySymbol,
                accountBalance =formattedAccountBalance,
                incomeDataWithCategory = incomeDataWithCategory,
                expenseDataWithCategory = expenseDataWithCategory
            )
            getBudget()
        }
    }

    private fun getBudget(){
        viewModelScope.launch(Dispatchers.IO) {
            val userId2 = homePageUseCaseWrapper.getUIDLocally() ?: "Unknown"
            val selectedYear = Calendar.getInstance().get(Calendar.YEAR)
            val selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
            val budget = homePageUseCaseWrapper.getBudgetLocalUseCase(
                userId = userId2,
                month = selectedMonth,
                year = selectedYear
            )

            if(budget != null){
                _homePageStates.value = homePageStates.value.copy(
                    monthlyBudget = budget.amount,
                    receiveAlert = budget.thresholdAmount,
                    budgetExist = true
                )
                sendNotification()
            }
        }
    }

    private fun sendNotification(){
        viewModelScope.launch(Dispatchers.IO) {
            val alertNotification = (_homePageStates.value.expenseAmount.toDouble()/_homePageStates.value.monthlyBudget) * 100

            Log.d("HomePageViewModelN","Alert Notification $alertNotification")
            Log.d("HomePageViewModelN","Receive Alert ${_homePageStates.value.receiveAlert}")

            if(alertNotification >= _homePageStates.value.receiveAlert){
                Log.d("HomePageViewModelN","Inside If")
                homePageUseCaseWrapper.sendBudgetNotificationUseCase(title = "Alert", message = "You have crossed your ${_homePageStates.value.receiveAlert}% of your budget")
                Log.d("HomePageViewModelN","Notification Sent")
            }
        }
    }
}