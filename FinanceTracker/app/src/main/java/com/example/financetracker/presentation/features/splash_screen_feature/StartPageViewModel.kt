package com.example.financetracker.presentation.features.splash_screen_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.financetracker.domain.usecases.usecase_wrapper.CoreUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StartPageViewModel @Inject constructor(
    private val coreUseCasesWrapper: CoreUseCasesWrapper
): ViewModel(){


    private val _startUpPageStates = MutableStateFlow(StartUpPageStates())
    val startUpPageStates : StateFlow<StartUpPageStates> = _startUpPageStates.asStateFlow()

    fun onEvent(startUpPageEvents: StartUpPageEvents){
        when(startUpPageEvents){
            is StartUpPageEvents.ChangeSelectedButton -> {
                _startUpPageStates.value = startUpPageStates.value.copy(
                    selectedButton = startUpPageEvents.button
                )
            }

            is StartUpPageEvents.ChangePreviousSelectedButton -> {
                _startUpPageStates.value = startUpPageStates.value.copy(
                    previousSelectedButton = startUpPageEvents.button
                )
            }
        }
    }

    init {
        checkUserLogin()
    }

    private fun checkUserLogin() {
        _startUpPageStates.value = startUpPageStates.value.copy(
            isLoggedIn = coreUseCasesWrapper.checkIsLoggedInUseCase()
        )
        Log.d("StartUpViewModel","Is Logged In: ${coreUseCasesWrapper.checkIsLoggedInUseCase()}")
    }
}