package com.example.financetracker.setup_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.setup_account.domain.usecases.UseCasesWrapperSetupAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetUpAccountPageViewModel @Inject constructor(
    private val useCasesWrapperSetupAccount: UseCasesWrapperSetupAccount
): ViewModel() {


    private val _setupAccountPageStates = MutableStateFlow(SetUpAccountPageStates())
    val setUpAccountPageStates : StateFlow<SetUpAccountPageStates> = _setupAccountPageStates

    fun setUserEmail(){
        viewModelScope.launch {
            val userEmail = useCasesWrapperSetupAccount.getUserEmailUserCase()
            _setupAccountPageStates.value = setUpAccountPageStates.value.copy(
                email = userEmail
            )
        }

    }


}