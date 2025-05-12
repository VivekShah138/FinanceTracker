package com.example.financetracker.main_page_feature.view_records.transactions.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionOrder
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionTypeFilter
import com.example.financetracker.main_page_feature.view_records.use_cases.ViewRecordsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewTransactionsViewModel @Inject constructor(
    private val viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
): ViewModel() {

    private val _viewTransactionStates = MutableStateFlow(ViewTransactionsStates())
    val viewTransactionStates : StateFlow<ViewTransactionsStates> = _viewTransactionStates.asStateFlow()

    private val _selectedItem = MutableStateFlow<Transactions?>(null)
    val selectedItem: StateFlow<Transactions?> = _selectedItem.asStateFlow()

    private val uid = viewRecordsUseCaseWrapper.getUIDLocally() ?: "Unknown"

    init {
        updateCategoryState()
        getTransactionsAllFilter()
    }

    fun onEvent(viewTransactionsEvents: ViewTransactionsEvents){
        when(viewTransactionsEvents){

            // Transaction Duration Dropdown
            is ViewTransactionsEvents.SelectTransactionsDuration -> {

                Log.d("ViewTransactionsViewModel","RangeDropDown Before: ${_viewTransactionStates.value.rangeDropDownExpanded}")
                Log.d("ViewTransactionsViewModel","SelectedDuration Before: ${_viewTransactionStates.value.selectedDuration}")

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    rangeDropDownExpanded = viewTransactionsEvents.expanded,
                    selectedDuration = viewTransactionsEvents.duration,
                    filters = viewTransactionStates.value.filters.map {
                        if (it is TransactionFilter.Duration) {
                            it.copy(viewTransactionsEvents.duration) // Update the categories filter with fetched categories
                        } else {
                            it
                        }
                    }
                )

                Log.d("ViewTransactionsViewModel","RangeDropDown After: ${_viewTransactionStates.value.rangeDropDownExpanded}")
                Log.d("ViewTransactionsViewModel","SelectedDuration After: ${_viewTransactionStates.value.selectedDuration}")
                Log.d("ViewTransactionsViewModelF","SelectedDuration Filter After: ${_viewTransactionStates.value.filters}")

                getTransactionsAllFilter()
            }

            is ViewTransactionsEvents.GetSingleTransaction -> {

                viewModelScope.launch(Dispatchers.IO) {

                    val transaction = viewRecordsUseCaseWrapper.getAllLocalTransactionsById(viewTransactionsEvents.transactionId)
                    Log.d("ViewTransactionsViewModelS","SavedItems Before ${_selectedItem.value}")
                    _selectedItem.value = transaction
                    Log.d("ViewTransactionsViewModelS","SavedItems After ${_selectedItem.value}")

                }

            }

            is ViewTransactionsEvents.SelectItems -> {

                Log.d("ViewTransactionsViewModelS","SavedItems Before ${_selectedItem.value}")
                _selectedItem.value = viewTransactionsEvents.transactions
                Log.d("ViewTransactionsViewModelS","SavedItems After ${_selectedItem.value}")

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
                    customDeleteAlertBoxState = viewTransactionsEvents.state
                )
            }

            is ViewTransactionsEvents.SelectTransactionsFilterCategories -> {
                _viewTransactionStates.value = _viewTransactionStates.value.copy(
                    filters = viewTransactionStates.value.filters.map {
                        if (it is TransactionFilter.Category) {
                            it.copy(viewTransactionsEvents.categories) // Update the categories filter with fetched categories
                        } else {
                            it
                        }
                    }
                )
            }

            is ViewTransactionsEvents.SelectTransactionsFilterOrder -> {
                _viewTransactionStates.value = _viewTransactionStates.value.copy(
                    filters = viewTransactionStates.value.filters.map {
                        if (it is TransactionFilter.Order) {
                            it.copy(viewTransactionsEvents.order) // Update the categories filter with fetched categories
                        } else {
                            it
                        }
                    }
                )
            }

            is ViewTransactionsEvents.SelectTransactionsFilterType -> {
                _viewTransactionStates.value = _viewTransactionStates.value.copy(
                    filters = viewTransactionStates.value.filters.map {
                        if (it is TransactionFilter.TransactionType) {
                            it.copy(viewTransactionsEvents.type) // Update the categories filter with fetched categories
                        } else {
                            it
                        }
                    }
                )
            }
            is ViewTransactionsEvents.UpdateFilter -> {
                val updatedFilters = viewTransactionStates.value.filters.toMutableList()
                Log.d("ViewTransactionsViewModelFilter","updateFilters:  $updatedFilters")
                Log.d("ViewTransactionsViewModelFilter","stateFiltersBefore:  ${_viewTransactionStates.value.filters}")

                when (val filter = viewTransactionsEvents.filter) {
                    is TransactionFilter.Category -> {

                        if(filter.selectedCategories.isEmpty()){
                            Log.d("ViewTransactionsViewModelFilter","categories are empty")
                            _viewTransactionStates.value = viewTransactionStates.value.copy(
                                filterApplyButton = false
                            )
                        }
                        else{
                            _viewTransactionStates.value = viewTransactionStates.value.copy(
                                filterApplyButton = true
                            )
                        }
                        val existingFilter = updatedFilters.filterIsInstance<TransactionFilter.Category>().firstOrNull()

                        Log.d("ViewTransactionsViewModelFilter","existing Filter:  $existingFilter")
                        Log.d("ViewTransactionsViewModelFilter","filter Selected Categories:  ${filter.selectedCategories}")
                        val newSelectedCategories = if (existingFilter != null) {
                            filter.selectedCategories // already merged in UI
                        } else {
                            filter.selectedCategories
                        }
                        updatedFilters.removeAll { it is TransactionFilter.Category }
                        updatedFilters.add(TransactionFilter.Category(newSelectedCategories))
                    }
                    else -> {
                        updatedFilters.removeAll { it::class == filter::class }
                        updatedFilters.add(filter)
                        updateCategoryState()
                    }
                }

                _viewTransactionStates.value = viewTransactionStates.value.copy(filters = updatedFilters)

                Log.d("ViewTransactionsViewModelFilter","stateFiltersAfter:  ${_viewTransactionStates.value.filters}")

            }
            is ViewTransactionsEvents.ApplyFilter -> {
                Log.d("ViewTransactionsViewModelApply","Filter State: ${_viewTransactionStates.value.filters}")
                getTransactionsAllFilter()
            }
            is ViewTransactionsEvents.ClearFilter -> {
                Log.d("ViewTransactionsViewModelApply","Filter Old State: ${_viewTransactionStates.value.filters}")
                val defaultFilters: List<TransactionFilter> = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both), // Default transaction type
                    TransactionFilter.Order(TransactionOrder.Descending), // Default to Ascending order
                    TransactionFilter.Category(_viewTransactionStates.value.categories), // Default to all categories (empty list means no category filter)
                    TransactionFilter.Duration(DurationFilter.ThisMonth) // Default to "This Month" filter
                )

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    filters = defaultFilters
                )
                getTransactionsAllFilter()
            }

            // Delete Selected Transaction
            is ViewTransactionsEvents.DeleteSelectedTransactions -> {
                viewModelScope.launch(Dispatchers.IO) {

//
                    val selectedSingleTransaction = _selectedItem.value?.transactionId?.let { mutableSetOf(it) }

                    val selectedIds = if(_viewTransactionStates.value.selectedTransactions.isEmpty()) selectedSingleTransaction else _viewTransactionStates.value.selectedTransactions
                    selectedIds!!.forEach { selectedTransactionId ->

                        val selectedTransaction =
                            viewRecordsUseCaseWrapper.getAllLocalTransactionsById(
                                selectedTransactionId
                            )

                        if (selectedTransaction.cloudSync) {
                            viewRecordsUseCaseWrapper.insertDeletedTransactionsLocally(
                                DeletedTransactions(
                                    transactionId = selectedTransactionId,
                                    userUid = uid
                                )
                            )
                        }

                        // 1. Delete locally
                        viewRecordsUseCaseWrapper.deleteSelectedTransactionsByIdsLocally(
                            selectedTransactionId
                        )
                    }

                    _viewTransactionStates.value = _viewTransactionStates.value.copy(
                        isSelectionMode = false,
                        selectedTransactions = emptySet()
                    )
                    getTransactionsAllFilter()
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

            ViewTransactionsEvents.SelectAllTransactions -> {

                val selectedTransactions = _viewTransactionStates.value.selectedTransactions.toMutableSet()

                _viewTransactionStates.value.transactionsList.forEach { transaction ->
                    selectedTransactions.add(transaction.transactionId ?: 0)
                }

                _viewTransactionStates.value = _viewTransactionStates.value.copy(
                    selectedTransactions = selectedTransactions
                )
            }
        }
    }

    private fun updateCategoryState() {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedType = _viewTransactionStates.value.filters
                .filterIsInstance<TransactionFilter.TransactionType>()
                .firstOrNull()?.type ?: TransactionTypeFilter.Both

            Log.d("ViewTransactionsViewModelType","SelectedTransaction Type: $selectedType ")

            val allCategories = when (selectedType) {
                TransactionTypeFilter.Income -> viewRecordsUseCaseWrapper.getAllCategories(type = "income", uid).first()
                TransactionTypeFilter.Expense -> viewRecordsUseCaseWrapper.getAllCategories("expense", uid).first()
                TransactionTypeFilter.Both -> {
                    val expense = viewRecordsUseCaseWrapper.getAllCategories("expense", uid).first()
                    val income = viewRecordsUseCaseWrapper.getAllCategories("income", uid).first()
                    expense + income
                }
            }

            _viewTransactionStates.value = _viewTransactionStates.value.copy(
                filters = _viewTransactionStates.value.filters.map {
                    if (it is TransactionFilter.Category) {
                        it.copy(selectedCategories = allCategories)
                    } else it
                },
                categories = allCategories
            )

            Log.d("ViewTransactionsViewModelF", "Updated categories based on type: $selectedType, categories: $allCategories")
        }
    }

    private fun getTransactionsAllFilter(){
        Log.d("ViewTransactionsViewModelF","transaction Before ViewScope Filter")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewTransactionsViewModelF","transaction Enter ViewScope Filter")
            val transactions = viewRecordsUseCaseWrapper.getAllTransactionsFilters(uid = uid, filters = _viewTransactionStates.value.filters).first()
            Log.d("ViewTransactionsViewModelF","collected Transactions are $transactions")

            _viewTransactionStates.value = viewTransactionStates.value.copy(
                transactionsList = transactions
            )
            Log.d("ViewTransactionsViewModelF","transaction List Filter ${_viewTransactionStates.value.transactionsList}")
            Log.d("ViewTransactionsViewModelF","transaction Filter ${_viewTransactionStates.value.filters}")

            getUserProfile()
            getTotalAmount()
        }


    }

    private fun getTotalAmount(){
        val transactions = _viewTransactionStates.value.transactionsList
        Log.d("ViewTransactionsViewModelT","All Transactions $transactions")

        var incomeAmount = 0.0
        var expenseAmount = 0.0

        transactions.forEach {transaction ->

            when {
                transaction.transactionType.equals("Income", ignoreCase = true) -> {
                    incomeAmount += transaction.amount
                    Log.d("ViewTransactionsViewModelT","income Amount OverAll $incomeAmount")
                }
                transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                    expenseAmount += transaction.amount
                    Log.d("ViewTransactionsViewModelT","expense Amount OverAll $expenseAmount")
                }
            }
        }
        val totalAmount = incomeAmount - expenseAmount
        Log.d("ViewTransactionsViewModelT","Total Amount $totalAmount")

        _viewTransactionStates.value = viewTransactionStates.value.copy(
            totalAmount = incomeAmount - expenseAmount
        )

        Log.d("ViewTransactionsViewModelT","Total Amount State ${_viewTransactionStates.value.totalAmount}")

    }

    fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            val userProfile = viewRecordsUseCaseWrapper.getUserProfileLocal()
            val baseCurrency = userProfile?.baseCurrency ?: emptyMap()
            val currencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"

            _viewTransactionStates.value = viewTransactionStates.value.copy(
                currencySymbol = currencySymbol,
                baseCurrency = baseCurrency
            )
        }
    }
}