package com.example.financetracker.auth_feature.presentation.register

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
class RegisterPageViewModel @Inject constructor(
    private val authFeatureUseCasesWrapper: AuthFeatureUseCasesWrapper
):ViewModel(){

    private val _registerState = MutableStateFlow(RegisterPageStates())
    val registerState : StateFlow<RegisterPageStates> = _registerState.asStateFlow()

    private val registerEventChannel = Channel<RegisterEvent>()
    val registerEvents = registerEventChannel.receiveAsFlow()

    fun onEvent(registerPageEvents: RegisterPageEvents){
        when(registerPageEvents){
            is RegisterPageEvents.ChangeEmail -> {
                _registerState.value = registerState.value.copy(
                    email = registerPageEvents.email,
                    emailError = null
                )
            }
//            is RegisterPageEvents.ChangeUserName -> {
//                _registerState.value = registerState.value.copy(
//                    userName = registerPageEvents.name
//                )
//            }
            is RegisterPageEvents.ChangePassword -> {
                _registerState.value = registerState.value.copy(
                    password = registerPageEvents.password,
                    passwordError = null
                )
            }
            is RegisterPageEvents.ChangeConfirmPassword -> {
                _registerState.value = registerState.value.copy(
                    confirmPassword = registerPageEvents.confirmPassword,
                    confirmPasswordError = null
                )
            }
            RegisterPageEvents.SubmitRegister -> {
                viewModelScope.launch {
                    validateFields()
                }
            }
            is RegisterPageEvents.RegistrationSuccess -> {
                viewModelScope.launch {
                    registerEventChannel.send(RegisterEvent.Success(registerState.value.email))
                }
            }
            is RegisterPageEvents.RegistrationFailure -> {
                viewModelScope.launch {
                    registerEventChannel.send(RegisterEvent.Error(registerPageEvents.error))
                }
            }

        }
    }

    private suspend fun validateFields(){

        val emailResult = authFeatureUseCasesWrapper.validateEmail(registerState.value.email)
//        val nameResult = useCasesWrapper.validateName(registerState.value.userName)
        val passwordResult = authFeatureUseCasesWrapper.validatePassword(registerState.value.password)
        val confirmPasswordResult = authFeatureUseCasesWrapper.validateConfirmPassword(
            registerState.value.password,
            registerState.value.confirmPassword
        )

        val hasError = listOf(
            emailResult,
//            nameResult,
            passwordResult,
            confirmPasswordResult
        )

        if (hasError.any { !it.isSuccessful }) {
            _registerState.value = registerState.value.copy(
                emailError = emailResult.errorMessage,
//                userNameError = nameResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage
            )
            registerEventChannel.send(RegisterEvent.Error("Validation failed! Check errors."))
            return
        }

        viewModelScope.launch {
            _registerState.value = registerState.value.copy(
                emailError = emailResult.errorMessage,
//                userNameError = nameResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage
            )
            registerEventChannel.send(RegisterEvent.TriggerFirebaseRegistration)
        }


    }


    sealed class RegisterEvent{
        data class Success(val username: String): RegisterEvent()
        data class Error(val errorMessage: String?): RegisterEvent()
        data object TriggerFirebaseRegistration: RegisterEvent()
    }
}