package com.example.financetracker.presentation.features.view_records_feature.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.domain.model.DeletedTransactions
import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.utils.DurationFilter
import com.example.financetracker.utils.TransactionFilter
import com.example.financetracker.utils.TransactionOrder
import com.example.financetracker.utils.TransactionTypeFilter
import com.example.financetracker.domain.usecases.usecase_wrapper.ViewRecordsUseCaseWrapper
import com.example.financetracker.presentation.features.view_records_feature.events.ViewTransactionsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewTransactionsStates
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

    private val uid = viewRecordsUseCaseWrapper.getUIDLocalUseCase() ?: "Unknown"

    init {
        updateCategoryState()
        getTransactionsAllFilter()
    }

    fun onEvent(viewTransactionsEvents: ViewTransactionsEvents){
        when(viewTransactionsEvents){

            // Transaction Duration Dropdown
            is ViewTransactionsEvents.SelectTransactionsDuration -> {
                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    rangeDropDownExpanded = viewTransactionsEvents.expanded,
                    selectedDuration = viewTransactionsEvents.duration,
                    filters = viewTransactionStates.value.filters.map {
                        if (it is TransactionFilter.Duration) {
                            it.copy(durationFilter = viewTransactionsEvents.duration) // Update the categories filter with fetched categories
                        } else {
                            it
                        }
                    }
                )
                getTransactionsAllFilter()
            }

            is ViewTransactionsEvents.GetSingleTransaction -> {

                viewModelScope.launch(Dispatchers.IO) {

                    val transaction = viewRecordsUseCaseWrapper.getTransactionByIdLocalUseCase(viewTransactionsEvents.transactionId)
                    _selectedItem.value = transaction
                }

            }

            is ViewTransactionsEvents.SelectItems -> {
                _selectedItem.value = viewTransactionsEvents.transactions
            }

            // Transaction Filter
            is ViewTransactionsEvents.SelectTransactionsFilter -> {
                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    filterBottomSheetState = viewTransactionsEvents.state
                )
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
                            it.copy(order = viewTransactionsEvents.order) // Update the categories filter with fetched categories
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
                            it.copy(type = viewTransactionsEvents.type) // Update the categories filter with fetched categories
                        } else {
                            it
                        }
                    }
                )
            }
            is ViewTransactionsEvents.UpdateFilter -> {
                val updatedFilters = viewTransactionStates.value.filters.toMutableList()
                when (val filter = viewTransactionsEvents.filter) {
                    is TransactionFilter.Category -> {

                        if(filter.selectedCategories.isEmpty()){
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
                        val newSelectedCategories = if (existingFilter != null) {
                            filter.selectedCategories
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

            }
            is ViewTransactionsEvents.ApplyFilter -> {
                getTransactionsAllFilter()
            }
            is ViewTransactionsEvents.ClearFilter -> {
                val defaultFilters: List<TransactionFilter> = listOf(
                    TransactionFilter.TransactionType(TransactionTypeFilter.Both),
                    TransactionFilter.Order(TransactionOrder.Descending),
                    TransactionFilter.Category(_viewTransactionStates.value.categories),
                    TransactionFilter.Duration(DurationFilter.ThisMonth)
                )

                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    filters = defaultFilters
                )
                getTransactionsAllFilter()
            }

            // Delete Selected Transaction
            is ViewTransactionsEvents.DeleteSelectedTransactions -> {
                viewModelScope.launch(Dispatchers.IO) {

                    val selectedSingleTransaction = _selectedItem.value?.transactionId?.let { mutableSetOf(it) }

                    val selectedIds = _viewTransactionStates.value.selectedTransactions.ifEmpty { selectedSingleTransaction }
                    selectedIds!!.forEach { selectedTransactionId ->

                        val selectedTransaction =
                            viewRecordsUseCaseWrapper.getTransactionByIdLocalUseCase(
                                selectedTransactionId
                            )

                        if (selectedTransaction.cloudSync) {
                            viewRecordsUseCaseWrapper.insertDeletedTransactionsLocalUseCase(
                                DeletedTransactions(
                                    transactionId = selectedTransactionId,
                                    userUid = uid
                                )
                            )
                        }

                        // 1. Delete locally
                        viewRecordsUseCaseWrapper.deleteTransactionByIdLocalUseCase(
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
                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    isSelectionMode = false,
                    selectedTransactions = emptySet()
                )
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

            val allCategories = when (selectedType) {
                TransactionTypeFilter.Income -> viewRecordsUseCaseWrapper.getAllCategoriesLocalUseCase(type = "income", uid).first()
                TransactionTypeFilter.Expense -> viewRecordsUseCaseWrapper.getAllCategoriesLocalUseCase("expense", uid).first()
                TransactionTypeFilter.Both -> {
                    val expense = viewRecordsUseCaseWrapper.getAllCategoriesLocalUseCase("expense", uid).first()
                    val income = viewRecordsUseCaseWrapper.getAllCategoriesLocalUseCase("income", uid).first()
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
        }
    }

    private fun getTransactionsAllFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            val transactions = viewRecordsUseCaseWrapper.getAllTransactionsFilters(uid = uid, filters = _viewTransactionStates.value.filters).first()

            _viewTransactionStates.value = viewTransactionStates.value.copy(
                transactionsList = transactions
            )
            getUserProfile()
            getTotalAmount()
        }
    }

    private fun getTotalAmount(){
        val transactions = _viewTransactionStates.value.transactionsList
        var incomeAmount = 0.0
        var expenseAmount = 0.0

        transactions.forEach {transaction ->

            when {
                transaction.transactionType.equals("Income", ignoreCase = true) -> {
                    incomeAmount += transaction.amount }
                transaction.transactionType.equals("Expense", ignoreCase = true) -> {
                    expenseAmount += transaction.amount
                }
            }
        }
        _viewTransactionStates.value = viewTransactionStates.value.copy(
            totalAmount = incomeAmount - expenseAmount
        )
    }

    private fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            val userProfile = viewRecordsUseCaseWrapper.getUserProfileLocalUseCase()
            val baseCurrency = userProfile?.baseCurrency ?: emptyMap()
            val currencySymbol = userProfile?.baseCurrency?.entries?.firstOrNull()?.value?.symbol ?: "$"

            _viewTransactionStates.value = viewTransactionStates.value.copy(
                currencySymbol = currencySymbol,
                baseCurrency = baseCurrency
            )
        }
    }
}