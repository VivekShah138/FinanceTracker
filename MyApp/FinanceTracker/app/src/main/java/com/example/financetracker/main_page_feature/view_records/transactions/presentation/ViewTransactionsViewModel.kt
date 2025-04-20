package com.example.financetracker.main_page_feature.view_records.transactions.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionsEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.view_records.use_cases.ViewRecordsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ViewTransactionsViewModel @Inject constructor(
    private val viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
): ViewModel() {

    private val _viewTransactionStates = MutableStateFlow(ViewTransactionsStates())
    val viewTransactionStates : StateFlow<ViewTransactionsStates> = _viewTransactionStates.asStateFlow()

    private val uid = viewRecordsUseCaseWrapper.getUIDLocally() ?: "Unknown"

    init {
        getTransactionsAll()
    }

    fun onEvent(viewTransactionsEvents: ViewTransactionsEvents){
        when(viewTransactionsEvents){

            // Load Transactions
            is ViewTransactionsEvents.LoadTransactionsAll -> {
                getTransactionsAll()
            }
            is ViewTransactionsEvents.LoadTransactionsCustomDate -> {
//                getTransactionsCustomDate()
            }
            is ViewTransactionsEvents.LoadTransactionsLast3Month -> {
                getTransactionsLast3Months()
            }
            is ViewTransactionsEvents.LoadTransactionsLastMonth -> {
                getTransactionsLastMonth()
            }
            is ViewTransactionsEvents.LoadTransactionsThisMonth -> {
                getTransactionsThisMonth()
            }
            is ViewTransactionsEvents.LoadTransactionsToday -> {
                getTransactionsToday()
            }

            // Transaction Duration Dropdown
            is ViewTransactionsEvents.SelectTransactionsDuration -> {

                Log.d("ViewTransactionsViewModel","RangeDropDown Before: ${_viewTransactionStates.value.rangeDropDownExpanded}")
                Log.d("ViewTransactionsViewModel","SelectedDuration Before: ${_viewTransactionStates.value.selectedDuration}")

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    rangeDropDownExpanded = viewTransactionsEvents.expanded,
                    selectedDuration = viewTransactionsEvents.duration
                )

                Log.d("ViewTransactionsViewModel","RangeDropDown After: ${_viewTransactionStates.value.rangeDropDownExpanded}")
                Log.d("ViewTransactionsViewModel","SelectedDuration After: ${_viewTransactionStates.value.selectedDuration}")
            }

            // Transaction Filter
            is ViewTransactionsEvents.SelectTransactionsFilter -> {

                Log.d("ViewTransactionsViewModel","BottomStateAfter Before: ${_viewTransactionStates.value.filterBottomSheetState}")

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    filterBottomSheetState = viewTransactionsEvents.state
                )

                Log.d("ViewTransactionsViewModel","BottomStateAfter After: ${_viewTransactionStates.value.filterBottomSheetState}")
            }

            is ViewTransactionsEvents.ChangeCustomDateAlertBox -> {
                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    customDateAlertBoxState = viewTransactionsEvents.state
                )
            }

            // Delete Selected Transaction
            is ViewTransactionsEvents.DeleteSelectedTransactions -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val selectedIds = _viewTransactionStates.value.selectedTransactions
                    val isCloudSync = viewRecordsUseCaseWrapper.getCloudSyncLocally()
                    val isInternetAvailable = viewRecordsUseCaseWrapper.internetConnectionAvailability() // You can implement this using `ConnectivityManager`


                    selectedIds.forEach { selectedTransactionId ->


                        val selectedTransaction = viewRecordsUseCaseWrapper.getAllLocalTransactionsById(selectedTransactionId)

                        if(selectedTransaction.cloudSync){
                            viewRecordsUseCaseWrapper.insertDeletedTransactionsLocally(
                                DeletedTransactions(
                                    transactionId = selectedTransactionId,
                                    userUid = uid
                                )
                            )
                        }

                        // 1. Delete locally
                        viewRecordsUseCaseWrapper.deleteSelectedTransactionsByIdsLocally(selectedTransactionId)
                    }

                    _viewTransactionStates.value = _viewTransactionStates.value.copy(
                        isSelectionMode = false,
                        selectedTransactions = emptySet()
                    )
                }


            }

            // Selection Mode
            is ViewTransactionsEvents.EnterSelectionMode -> {

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    isSelectionMode = true
                )
            }
            is ViewTransactionsEvents.ExitSelectionMode -> {

                Log.d("ViewTransactionsViewModel","Exit Selection Mode SelectedTransaction Before delete: ${_viewTransactionStates.value.selectedTransactions}")

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    isSelectionMode = false,
                    selectedTransactions = emptySet()
                )

                Log.d("ViewTransactionsViewModel","Exit Selection Mode SelectedTransaction After delete: ${_viewTransactionStates.value.selectedTransactions}")
            }
            is ViewTransactionsEvents.ToggleTransactionSelection -> {
                val current = _viewTransactionStates.value.selectedTransactions.toMutableSet()
                if(current.contains(viewTransactionsEvents.transactionId)){
                    current.remove(viewTransactionsEvents.transactionId)
                }
                else{
                    current.add(viewTransactionsEvents.transactionId)
                }
                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    selectedTransactions = current
                )

                Log.d("ViewTransactionsViewModel","SelectedTransaction List: $current")
                Log.d("ViewTransactionsViewModel","SelectedTransaction state List: ${_viewTransactionStates.value.selectedTransactions}")
            }
        }
    }







    private fun getTransactionsAll(){
        viewModelScope.launch(Dispatchers.IO) {
            viewRecordsUseCaseWrapper.getAllTransactions(uid).collect { transactions ->
                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    transactionsList = transactions
                )
            }
            Log.d("ViewTransactionsViewModel","transaction List ${_viewTransactionStates.value.transactionsList}")

        }
    }

    private fun getTransactionsToday(){
        viewModelScope.launch(Dispatchers.IO) {
            viewRecordsUseCaseWrapper.getAllTransactions(uid).collect { transactions ->

                val startOfDay = getStartOfTodayInMillis()
                val endOfDay = getEndOfTodayInMillis()

                val todayTransactions = transactions.filter {
                    it.dateTime in startOfDay..endOfDay
                }

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    transactionsList = todayTransactions
                )
            }
            Log.d("ViewTransactionsViewModel","transaction List ${_viewTransactionStates.value.transactionsList}")



        }
    }

    private fun getStartOfTodayInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfTodayInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    private fun getTransactionsThisMonth() {
        viewModelScope.launch(Dispatchers.IO) {
            viewRecordsUseCaseWrapper.getAllTransactions(uid).collect { transactions ->

                val startOfMonth = getStartOfMonthInMillis()
                val endOfMonth = getEndOfTodayInMillis()

                Log.d("ViewTransactionsViewModel","start of Month date format $startOfMonth")
                Log.d("ViewTransactionsViewModel","end of Month date format $endOfMonth")

                val thisMonthTransactions = transactions.filter {
                    it.dateTime in startOfMonth..endOfMonth
                }

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    transactionsList = thisMonthTransactions
                )

                Log.d("ViewTransactionsViewModel", "transaction List ${_viewTransactionStates.value.transactionsList}")
            }
        }
    }


    private fun getStartOfMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to first day of current month
        Log.d("ViewTransactionsViewModel","This Month First Day ${Calendar.DAY_OF_MONTH}")
        calendar.set(Calendar.HOUR_OF_DAY, 0)  // Set to 00:00:00
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dateThisMonth = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.timeInMillis)
        Log.d("ViewTransactionsViewModel","This Month long${calendar.timeInMillis}")
        Log.d("ViewTransactionsViewModel","This Month date format $dateThisMonth")
        return calendar.timeInMillis
    }

    private fun getEndOfMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) // Set to last day of current month
        calendar.set(Calendar.HOUR_OF_DAY, 23)  // Set to 23:59:59
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }


    private fun getTransactionsLastMonth() {
        viewModelScope.launch(Dispatchers.IO) {
            viewRecordsUseCaseWrapper.getAllTransactions(uid).collect { transactions ->

                val startOfLastMonth = getStartOfLastMonthInMillis()
                val endOfLastMonth = getEndOfLastMonthInMillis()
                val startOfLastMonthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startOfLastMonth)
                val endOfLastMonthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(endOfLastMonth)
                Log.d("ViewTransactionsViewModel","Last Month start date format $startOfLastMonthDate")
                Log.d("ViewTransactionsViewModel","Last Month end date format $endOfLastMonthDate")

                val lastMonthTransactions = transactions.filter {
                    it.dateTime in startOfLastMonth..endOfLastMonth
                }

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    transactionsList = lastMonthTransactions
                )

                Log.d("ViewTransactionsViewModel", "transaction List ${_viewTransactionStates.value.transactionsList}")
            }
        }
    }


    private fun getStartOfLastMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)  // Move to the previous month
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to the first day of the last month
        calendar.set(Calendar.HOUR_OF_DAY, 0)  // Set to 00:00:00
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfLastMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)  // Move to the previous month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) // Set to the last day of the last month
        calendar.set(Calendar.HOUR_OF_DAY, 23)  // Set to 23:59:59
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    private fun getStartOfLast3MonthsInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -3)  // Move back by 3 months
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to the first day of that month
        calendar.set(Calendar.HOUR_OF_DAY, 0)  // Set to 00:00:00
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getTransactionsLast3Months() {
        viewModelScope.launch(Dispatchers.IO) {
            viewRecordsUseCaseWrapper.getAllTransactions(uid).collect { transactions ->

                val startOfLast3Months = getStartOfLast3MonthsInMillis()
                val endOfLast3Months = System.currentTimeMillis()  // Current time as the end
                val startOfLast3MonthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startOfLast3Months)
                val endOfLast3MonthDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(endOfLast3Months)
                Log.d("ViewTransactionsViewModel","Last Month start date format $startOfLast3MonthDate")
                Log.d("ViewTransactionsViewModel","Last Month end date format $endOfLast3MonthDate")

                val last3MonthsTransactions = transactions.filter {
                    it.dateTime in startOfLast3Months..endOfLast3Months
                }

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    transactionsList = last3MonthsTransactions
                )

                Log.d("ViewTransactionsViewModel", "transaction List ${_viewTransactionStates.value.transactionsList}")
            }
        }
    }

    private fun getTransactionsCustomDate(fromDate: Long, toDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            viewRecordsUseCaseWrapper.getAllTransactions(uid).collect { transactions ->

                val customDateTransactions = transactions.filter {
                    it.dateTime in fromDate..toDate
                }

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    transactionsList = customDateTransactions
                )

                Log.d("ViewTransactionsViewModel", "transaction List ${_viewTransactionStates.value.transactionsList}")
            }
        }
    }






}