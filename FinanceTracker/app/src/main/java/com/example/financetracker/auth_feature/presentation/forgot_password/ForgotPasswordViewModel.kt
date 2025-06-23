package com.example.financetracker.auth_feature.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.auth_feature.domain.usecases.AuthFeatureUseCasesWrapper

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authFeatureUseCasesWrapper: AuthFeatureUseCasesWrapper
): ViewModel(){

    private val _forgotPasswordState = MutableStateFlow(ForgotPasswordStates())
    val forgotPasswordState : StateFlow<ForgotPasswordStates> = _forgotPasswordState.asStateFlow()

    private val forgotPasswordEventChannel = Channel<ForgotPasswordValidationEvent>()
    val forgotPasswordValidationEvent = forgotPasswordEventChannel.receiveAsFlow()


    fun onEvent(forgotPasswordEvents: ForgotPasswordEvents){
        when(forgotPasswordEvents){
            is ForgotPasswordEvents.ChangeEmail -> {
                _forgotPasswordState.value = forgotPasswordState.value.copy(
                    email = forgotPasswordEvents.email
                )
            }
            is ForgotPasswordEvents.Submit -> {
                viewModelScope.launch {
                    emailValidationCheck()
                }
            }
            is ForgotPasswordEvents.EmailSendSuccess -> {
                viewModelScope.launch {
                    forgotPasswordEventChannel.send(ForgotPasswordValidationEvent.Success)
                }

            }
            is ForgotPasswordEvents.EmailSendFailure -> {
                viewModelScope.launch {
                    forgotPasswordEventChannel.send(ForgotPasswordValidationEvent.Failure(forgotPasswordEvents.errorMessage))
                }
            }
        }
    }

    private suspend fun emailValidationCheck(){
        val emailResult = authFeatureUseCasesWrapper.validateEmail(forgotPasswordState.value.email)
        if(!emailResult.isSuccessful){
            return
        }
        forgotPasswordEventChannel.send(ForgotPasswordValidationEvent.TriggerSendResetEmail)
    }

    sealed class ForgotPasswordValidationEvent{
        data object Success: ForgotPasswordValidationEvent()
        data class Failure(val error: String): ForgotPasswordValidationEvent()
        data object TriggerSendResetEmail: ForgotPasswordValidationEvent()
    }

}