package com.example.financetracker.main_page_feature.view_transactions.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.main_page_feature.view_transactions.ViewTransactionUseCaseWrapper
import com.example.financetracker.main_page_feature.view_transactions.presentation.components.ViewTransactionsStates
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewTransactionsViewModel @Inject constructor(
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper,
    private val viewTransactionUseCaseWrapper: ViewTransactionUseCaseWrapper
): ViewModel() {

    private val _viewTransactionStates = MutableStateFlow(ViewTransactionsStates())
    val viewTransactionStates : StateFlow<ViewTransactionsStates> = _viewTransactionStates.asStateFlow()

    private val uid = setupAccountUseCasesWrapper.getUIDLocally() ?: "Unknown"

    init {
        getTransactions()
    }

    fun onEvent(viewTransactionsEvents: ViewTransactionsEvents){
        when(viewTransactionsEvents){
            is ViewTransactionsEvents.LoadTransactions -> {
                getTransactions()
            }
        }
    }

    private fun getTransactions(){
        viewModelScope.launch(Dispatchers.IO) {
            viewTransactionUseCaseWrapper.getTransactionsLocally(uid).collect { transactions ->
                _viewTransactionStates.value = viewTransactionStates.value.copy(
                    transactionsList = transactions
                )
            }
            Log.d("ViewTransactionsViewModel","transaction List ${_viewTransactionStates.value.transactionsList}")

        }
    }

}