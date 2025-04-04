package com.example.financetracker.startup_page_feature

import androidx.lifecycle.ViewModel
import com.example.financetracker.core.core_domain.usecase.CoreUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StartPageViewModel @Inject constructor(
    private val coreUseCasesWrapper: CoreUseCasesWrapper
): ViewModel(){
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn



    init {
        checkUserLogin()
    }

    private fun checkUserLogin() {
        _isLoggedIn.value = coreUseCasesWrapper.checkIsLoggedInUseCase()
    }
}