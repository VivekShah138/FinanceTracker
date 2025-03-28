package com.example.financetracker.auth_feature.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.auth_feature.domain.usecases.UseCasesWrapper
import com.example.financetracker.auth_feature.presentation.forgot_password.ResetPasswordWithCredentialResult
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.domain.usecase.UseCasesWrapperCore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginPageViewModel @Inject constructor(
    private val useCasesWrapper: UseCasesWrapper,
    private val useCasesWrapperCore: UseCasesWrapperCore
): ViewModel(){

    private val _loginState = MutableStateFlow(LoginPageStates())
    val loginState : StateFlow<LoginPageStates> = _loginState.asStateFlow()

    private val loginEventChannel = Channel<LoginEvent>()
    val loginEvents = loginEventChannel.receiveAsFlow()

    fun onEvent(loginPageEvents: LoginPageEvents){
        when(loginPageEvents){
            is LoginPageEvents.ChangeEmail->{
                _loginState.value = loginState.value.copy(
                    email = loginPageEvents.email
                )
            }
            is LoginPageEvents.ChangePassword->{
                _loginState.value = loginState.value.copy(
                    password = loginPageEvents.password
                )
            }
            is LoginPageEvents.ChangeKeepLoggedIn -> {
                _loginState.value = loginState.value.copy(
                    keepLoggedIn = loginPageEvents.keepLoggedIn
                )
            }
            is LoginPageEvents.ClickLoginWithGoogle -> {
                viewModelScope.launch {
                    when(loginPageEvents.result){
                        is GoogleSignInResult.Success->{
                            _loginState.value = loginState.value.copy(
                                loggedInUser = loginPageEvents.result.username
                            )
                            useCasesWrapper.keepUserLoggedIn(_loginState.value.keepLoggedIn)
                            handleUserProfile()
                            loginEventChannel.send(LoginEvent.Success(loginPageEvents.result.username))
                        }
                        is GoogleSignInResult.Cancelled->{
                            _loginState.value = loginState.value.copy(
                                errorMessage = "Google Cancelled"
                            )
                            loginEventChannel.send(LoginEvent.Error(loginState.value.errorMessage))
                        }
                        is GoogleSignInResult.Failure->{
                            _loginState.value = loginState.value.copy(
                                errorMessage = "Google SignIn Failed"
                            )
                            loginEventChannel.send(LoginEvent.Error(loginState.value.errorMessage))
                        }
                    }
                }
            }
            is LoginPageEvents.SubmitLogIn -> {
                viewModelScope.launch {
                    validateFields()
                }

            }

            is LoginPageEvents.LoginFailure -> {
                viewModelScope.launch {
                    loginEventChannel.send(LoginEvent.Error(loginPageEvents.error))
                }

            }
            is LoginPageEvents.LoginSuccess -> {
                viewModelScope.launch {
                    useCasesWrapper.keepUserLoggedIn(_loginState.value.keepLoggedIn)
                    handleUserProfile()
                    loginEventChannel.send(LoginEvent.Success(loginPageEvents.userName))
                }

            }
            is LoginPageEvents.ClickForgotPassword -> {
                viewModelScope.launch {
                    when(loginPageEvents.result){
                        is ResetPasswordWithCredentialResult.CredentialLoginSuccess -> {
                            loginEventChannel.send(LoginEvent.Success(loginPageEvents.result.email))
                        }
                        is ResetPasswordWithCredentialResult.Cancelled -> {
                            loginEventChannel.send(LoginEvent.Error("Cancelled"))
                        }
                        is ResetPasswordWithCredentialResult.UnknownFailure -> {
                            loginEventChannel.send(LoginEvent.Error("UnknownFailure"))
                        }
                    }
                }
            }
        }
    }

    private suspend fun validateFields() {

        val emailResult = useCasesWrapper.validateEmail(loginState.value.email)
        val passwordResult = useCasesWrapper.validatePassword(loginState.value.password)

        if(!emailResult.isSuccessful || !passwordResult.isSuccessful){
            _loginState.value = loginState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            loginEventChannel.send(LoginEvent.Error("Validation failed! Check errors."))
            return
        }

        loginEventChannel.send(LoginEvent.TriggerFirebaseLogIn)

    }

    private suspend fun handleUserProfile(){
        try {
            val userId = useCasesWrapperCore.getUserUIDUseCase() ?: "Unknown"
            val userProfile = useCasesWrapperCore.getUserProfileUseCase(userId)
            Log.d("LoginViewModel","state: ${userProfile}")
            if(userProfile == null){
                val email = useCasesWrapperCore.getUserEmailUserCase() ?: "Unknown"
                val newUserProfile = UserProfile(email = email, profileSetUpCompleted = false)
                useCasesWrapperCore.saveUserProfileUseCase(userId, newUserProfile)
                _loginState.value = loginState.value.copy(
                    userProfile = newUserProfile
                )
            }
            else{
                _loginState.value = loginState.value.copy(
                    userProfile = userProfile
                )
            }
        }catch (e:Exception){
            val errorMessage = e.localizedMessage
            loginEventChannel.send(LoginEvent.Error(errorMessage))
        }
    }

    sealed class LoginEvent{
        data class Success(val username: String): LoginEvent()
        data class Error(val errorMessage: String?): LoginEvent()
        data object TriggerFirebaseLogIn: LoginEvent()
    }
}

