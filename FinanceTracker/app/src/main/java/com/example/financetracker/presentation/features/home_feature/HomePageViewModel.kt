package com.example.financetracker.presentation.features.home_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.HomePageUseCaseWrapper
import com.example.financetracker.utils.DurationFilter
import com.example.financetracker.utils.TransactionFilter
import com.example.financetracker.utils.TransactionOrder
import com.example.financetracker.utils.TransactionTypeFilter
import com.example.financetracker.domain.usecases.usecase_wrapper.SetupAccountUseCasesWrapper
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

    private val _homeScreenStates = MutableStateFlow(HomeScreenStates())
    val homeScreenStates : StateFlow<HomeScreenStates> = _homeScreenStates.asStateFlow()

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
            val userProfile = homePageUseCaseWrapper.getUserProfileLocalUseCase()
            Logger.d(Logger.Tag.HOME_VIEWMODEL,"getUserProfile userProfile $userProfile")
        }
    }

    private fun updateCurrencyRatesOneTime(){
        viewModelScope.launch(Dispatchers.IO) {
            userCasesWrapperSetupAccount.seedCurrencyRatesLocalOneTime()
        }
    }

    private fun updateCurrencyRatesPeriodically(){
        viewModelScope.launch(Dispatchers.IO) {
            userCasesWrapperSetupAccount.seedCurrencyRatesLocalPeriodical()
        }
    }

    private fun getIncomeAndExpenseAmount() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = homePageUseCaseWrapper.getUIDLocalUseCase() ?: "Unknown"
            Logger.d(Logger.Tag.HOME_VIEWMODEL,"getIncomeAndExpenseAmount userId $userId")

            val allTransactions = homePageUseCaseWrapper.getAllTransactionsByUIDLocalUseCase(uid = userId).first()

            Logger.d(Logger.Tag.HOME_VIEWMODEL,"getIncomeAndExpenseAmount allTransactions $allTransactions")


            val allTransactionsThisMonth = homePageUseCaseWrapper.getAllTransactionsFilters(
                uid = userId,
                filters = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both),
                    TransactionFilter.Order(TransactionOrder.Ascending),
                    TransactionFilter.Duration(DurationFilter.ThisMonth)
                )
            ).first()


            val userProfile = homePageUseCaseWrapper.getUserProfileLocalUseCase()
            Logger.d(Logger.Tag.HOME_VIEWMODEL,"getIncomeAndExpenseAmount userProfile $userProfile")



            var incomeAmountThisMonth = 0.0
            var incomeAmountOverAll = 0.0
            var expenseAmountThisMonth = 0.0
            var expenseAmountOverAll = 0.0
            val baseCurrencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"

            Logger.d(Logger.Tag.HOME_VIEWMODEL,"getIncomeAndExpenseAmount baseCurrency $baseCurrencySymbol")


            val incomeDataWithCategory = _homeScreenStates.value.incomeDataWithCategory.toMutableMap().apply { clear() }
            val expenseDataWithCategory = _homeScreenStates.value.expenseDataWithCategory.toMutableMap().apply { clear() }


            allTransactionsThisMonth.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        incomeAmountThisMonth += transaction.amount
                        val currentIncomeAmount = incomeDataWithCategory[transaction.category] ?: 0.0
                        incomeDataWithCategory[transaction.category] = currentIncomeAmount + transaction.amount
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        expenseAmountThisMonth += transaction.amount
                        val currentExpenseAmount = expenseDataWithCategory[transaction.category] ?: 0.0
                        expenseDataWithCategory[transaction.category] = currentExpenseAmount + transaction.amount

                    }
                }
            }

            allTransactions.forEach { transaction ->
                when {
                    transaction.transactionType.equals("Income", ignoreCase = true) -> {
                        incomeAmountOverAll += transaction.amount
                    }
                    transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                        expenseAmountOverAll += transaction.amount
                    }
                }
            }

            val formattedIncomeThisMonth = String.format(Locale.US, "%.2f", incomeAmountThisMonth)
            val formattedExpenseThisMonth = String.format(Locale.US, "%.2f", expenseAmountThisMonth)
            val formattedAccountBalance = String.format(Locale.US, "%.2f", (incomeAmountOverAll - expenseAmountOverAll))

            _homeScreenStates.value = homeScreenStates.value.copy(
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
            val userId2 = homePageUseCaseWrapper.getUIDLocalUseCase() ?: "Unknown"
            val selectedYear = Calendar.getInstance().get(Calendar.YEAR)
            val selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
            val budget = homePageUseCaseWrapper.getBudgetLocalUseCase(
                userId = userId2,
                month = selectedMonth,
                year = selectedYear
            )

            if(budget != null){
                _homeScreenStates.value = homeScreenStates.value.copy(
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
            val alertNotification = (_homeScreenStates.value.expenseAmount.toDouble()/_homeScreenStates.value.monthlyBudget) * 100

            Logger.d(Logger.Tag.HOME_VIEWMODEL,"sendNotification Alert Notification $alertNotification")
            Logger.d(Logger.Tag.HOME_VIEWMODEL,"sendNotification Receive Alert ${_homeScreenStates.value.receiveAlert}")

            if(alertNotification >= _homeScreenStates.value.receiveAlert){
                homePageUseCaseWrapper.sendBudgetNotificationLocalUseCase(title = "Alert", message = "You have crossed your ${_homeScreenStates.value.receiveAlert}% of your budget")
                Logger.d(Logger.Tag.HOME_VIEWMODEL,"sendNotification Notification Sent")

            }
        }
    }
}