package com.example.financetracker.presentation.features.auth_feature.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.AuthFeatureUseCasesWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.BudgetUseCaseWrapper
import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.usecases.usecase_wrapper.CoreUseCasesWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.AddTransactionUseCasesWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.SavedItemsUseCasesWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.SetupAccountUseCasesWrapper
import com.example.financetracker.presentation.features.auth_feature.auth_utils.GoogleSignInResult
import com.example.financetracker.presentation.features.auth_feature.auth_utils.ResetPasswordWithCredentialResult
import com.example.financetracker.presentation.features.auth_feature.events.LoginPageEvents
import com.example.financetracker.presentation.features.auth_feature.states.LoginPageStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginPageViewModel @Inject constructor(
    private val authFeatureUseCasesWrapper: AuthFeatureUseCasesWrapper,
    private val coreUseCasesWrapper: CoreUseCasesWrapper,
    private val setupAccountUseCasesWrapper: SetupAccountUseCasesWrapper,
    private val savedItemsUseCasesWrapper: SavedItemsUseCasesWrapper,
    private val addTransactionUseCasesWrapper: AddTransactionUseCasesWrapper,
    private val budgetUseCaseWrapper: BudgetUseCaseWrapper
): ViewModel(){

    private val _loginState = MutableStateFlow(LoginPageStates())
    val loginState : StateFlow<LoginPageStates> = _loginState.asStateFlow()

    private val loginEventChannel = Channel<LoginEvent>()
    val loginEvents = loginEventChannel.receiveAsFlow()

    private val _startUpNavigationFlow = MutableSharedFlow<StartUpNavigation>()
    val startUpNavigationFlow = _startUpNavigationFlow.asSharedFlow()

    fun onEvent(loginPageEvents: LoginPageEvents){
        when(loginPageEvents){
            is LoginPageEvents.ChangeEmail ->{
                _loginState.value = loginState.value.copy(
                    email = loginPageEvents.email,
                    emailError = null
                )
            }
            is LoginPageEvents.ChangePassword ->{
                _loginState.value = loginState.value.copy(
                    password = loginPageEvents.password,
                    passwordError = null
                )
            }
            is LoginPageEvents.ClickLoginWithGoogle -> {
                viewModelScope.launch {
                    when(loginPageEvents.result){
                        is GoogleSignInResult.Success ->{
                            _loginState.value = loginState.value.copy(
                                loggedInUser = loginPageEvents.result.username,
                                isLoading = false
                            )
                            handleUserProfile(loginPageEvents.result.username)
                            loadRemoteDataItems()
                            loginEventChannel.send(LoginEvent.Success(loginPageEvents.result.username))
                        }
                        is GoogleSignInResult.Cancelled ->{
                            _loginState.value = loginState.value.copy(
                                errorMessage = "Google SignIn Cancelled",
                                isLoading = false
                            )
                            loginEventChannel.send(LoginEvent.Error(loginState.value.errorMessage))
                        }
                        is GoogleSignInResult.Failure ->{
                            _loginState.value = loginState.value.copy(
                                errorMessage = "SignIn Failed.Please Check Your Connection",
                                isLoading = false
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
                    handleUserProfile(loginPageEvents.userName)
                    loadRemoteDataItems()
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

            is LoginPageEvents.SetLoadingTrue -> {
                _loginState.value = loginState.value.copy(
                    isLoading = loginPageEvents.state
                )
            }
        }
    }

    private suspend fun validateFields() {

        val emailResult = authFeatureUseCasesWrapper.emailValidationUseCase(loginState.value.email)
        val passwordResult = authFeatureUseCasesWrapper.passwordValidationUseCase(loginState.value.password)

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

    private fun handleUserProfile(email: String) {
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true)
            try {
                val userId = withContext(Dispatchers.IO) {
                    coreUseCasesWrapper.getUserUIDRemoteUseCase() ?: "Unknown"
                }
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "userId: $userId")
                authFeatureUseCasesWrapper.insertUIDLocalUseCase(userId)

                val userProfile = withContext(Dispatchers.IO) {
                    coreUseCasesWrapper.getUserProfileRemoteUseCase(userId)
                }

                val finalProfile = userProfile ?: run {
                    val newProfile = UserProfile(email = email, profileSetUpCompleted = false)
                    withContext(Dispatchers.IO) {
                        coreUseCasesWrapper.saveUserProfileRemoteUseCase(userId, newProfile)
                    }
                    newProfile
                }
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "userProfile: $userProfile")

                val userName = listOfNotNull(finalProfile.firstName, finalProfile.lastName)
                    .joinToString(" ")
                coreUseCasesWrapper.setUserNameLocalUseCase(userName)

                _loginState.value = loginState.value.copy(userProfile = finalProfile)

                if(finalProfile.profileSetUpCompleted){
                    setupAccountUseCasesWrapper.keepUserLoggedInLocalUseCase(true)
                    _startUpNavigationFlow.emit(StartUpNavigation.NavigateToHome)
                }
                else{
                    _startUpNavigationFlow.emit(StartUpNavigation.NavigateToOnBoarding)
                }

            } catch (e: Exception) {
                Logger.e(Logger.Tag.LOGIN_VIEWMODEL, "Error in handleUserProfile", e)
                loginEventChannel.send(LoginEvent.Error(e.localizedMessage))
            } finally {
                _loginState.value = _loginState.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun loadRemoteDataItems() {
        _loginState.value = _loginState.value.copy(isDataSyncing = true)
        try {
            val userId = coreUseCasesWrapper.getUserUIDRemoteUseCase() ?: "Unknown"
            val isFirstTimeLoggedIn = setupAccountUseCasesWrapper.getFirstTimeLoggedIn(userId)
            Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "FirstTimeLoggedIn -> $isFirstTimeLoggedIn")
            if (isFirstTimeLoggedIn) {
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Starting insertRemoteSavedItemToLocal")
                savedItemsUseCasesWrapper.insertRemoteSavedItemToLocal()
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Completed insertRemoteSavedItemToLocal")

                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Starting insertRemoteTransactionsToLocal")
                addTransactionUseCasesWrapper.syncTransactionsRemoteToLocalUseCase()
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Completed insertRemoteTransactionsToLocal")

                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Starting insertUserProfileToLocalDb")
                coreUseCasesWrapper.insertUserProfileLocalUseCase()
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Completed insertUserProfileToLocalDb")

                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Starting insertRemoteBudgetsToLocal")
                budgetUseCaseWrapper.insertBudgetsRemoteToLocalUseCase()
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Completed insertRemoteBudgetsToLocal")

                setupAccountUseCasesWrapper.setFirstTimeLoginUseCase(uid = userId)
                setupAccountUseCasesWrapper.setCurrencyRatesUpdatedLocalUseCase(isUpdated = false)
                Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "FirstTimeLoggedIn set to false")
            }
        } catch (e: Exception) {
            Logger.d(Logger.Tag.LOGIN_VIEWMODEL, "Failed to sync: ${e.localizedMessage}")
        } finally {
            _loginState.value = _loginState.value.copy(isDataSyncing = false)
        }
    }

    sealed class LoginEvent{
        data class Success(val username: String): LoginEvent()
        data class Error(val errorMessage: String?): LoginEvent()
        data object TriggerFirebaseLogIn: LoginEvent()
    }

    sealed class StartUpNavigation{
        data object NavigateToHome: StartUpNavigation()
        data object NavigateToOnBoarding: StartUpNavigation()
    }
}

