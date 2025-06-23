package com.example.financetracker.auth_feature.presentation.login

import android.util.Log
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financetracker.auth_feature.domain.usecases.AuthFeatureUseCasesWrapper
import com.example.financetracker.auth_feature.presentation.forgot_password.ResetPasswordWithCredentialResult
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.core_domain.usecase.CoreUseCasesWrapper
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
import com.example.financetracker.setup_account.domain.usecases.SetupAccountUseCasesWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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

    fun onEvent(loginPageEvents: LoginPageEvents){
        when(loginPageEvents){
            is LoginPageEvents.ChangeEmail->{
                _loginState.value = loginState.value.copy(
                    email = loginPageEvents.email,
                    emailError = null
                )
            }
            is LoginPageEvents.ChangePassword->{
                _loginState.value = loginState.value.copy(
                    password = loginPageEvents.password,
                    passwordError = null
                )
            }
            is LoginPageEvents.ClickLoginWithGoogle -> {
                viewModelScope.launch {
                    when(loginPageEvents.result){
                        is GoogleSignInResult.Success->{
                            _loginState.value = loginState.value.copy(
                                loggedInUser = loginPageEvents.result.username,
                                isLoading = false
                            )
                            handleUserProfile()
                            loadRemoteDataItems()
                            loginEventChannel.send(LoginEvent.Success(loginPageEvents.result.username))
                        }
                        is GoogleSignInResult.Cancelled->{
                            _loginState.value = loginState.value.copy(
                                errorMessage = "Google SignIn Cancelled",
                                isLoading = false
                            )
                            loginEventChannel.send(LoginEvent.Error(loginState.value.errorMessage))
                        }
                        is GoogleSignInResult.Failure->{
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

                    handleUserProfile()
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

        val emailResult = authFeatureUseCasesWrapper.validateEmail(loginState.value.email)
        val passwordResult = authFeatureUseCasesWrapper.validatePassword(loginState.value.password)

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
        _loginState.value = _loginState.value.copy(isLoading = true)
        try {
            val userId = coreUseCasesWrapper.getUserUIDUseCase() ?: "Unknown"
            Log.d("LoginViewModel","userId: $userId")

            authFeatureUseCasesWrapper.insertUIDLocally(userId)
            Log.d("LoginViewModel","Inserted UserId")
            viewModelScope.launch(Dispatchers.IO) {
                val userId2 = authFeatureUseCasesWrapper.getUIDLocally()
                Log.d("LoginViewModel","UserId Local $userId2")
            }

            val userProfile = coreUseCasesWrapper.getUserProfileUseCase(userId)
            val userName = userProfile?.firstName + " " + userProfile?.lastName
            Log.d("LoginViewModel","userProfile: ${userProfile}")
            Log.d("LoginViewModel","userName: ${userName}")

            coreUseCasesWrapper.setUserNameLocally(userName)
            Log.d("LoginViewModel","userName: ${userName}")


            if(userProfile == null){
                val email = coreUseCasesWrapper.getUserEmailUserCase() ?: "Unknown"
                val newUserProfile = UserProfile(email = email, profileSetUpCompleted = false)
                coreUseCasesWrapper.saveUserProfileUseCase(userId, newUserProfile)
                _loginState.value = loginState.value.copy(
                    userProfile = newUserProfile
                )
                Log.d("LoginViewModel","KeepLogIn ${coreUseCasesWrapper.checkIsLoggedInUseCase}")
            }
            else{
                _loginState.value = loginState.value.copy(
                    userProfile = userProfile
                )
                if(userProfile.profileSetUpCompleted){
                    setupAccountUseCasesWrapper.keepUserLoggedIn(keepLoggedIn = true)
                }
            }
        }catch (e:Exception){
            val errorMessage = e.localizedMessage
            loginEventChannel.send(LoginEvent.Error(errorMessage))
        }finally {
            _loginState.value = _loginState.value.copy(isLoading = false)
        }
    }

//    private suspend fun loadRemoteDataItems(){
//        _loginState.value = _loginState.value.copy(isDataSyncing = true)
//        try {
//            val userId = coreUseCasesWrapper.getUserUIDUseCase() ?: "Unknown"
//            val isFirstTimeLoggedIn = setupAccountUseCasesWrapper.getFirstTimeLoggedIn(userId)
//            Log.d("AppEntry","FirstTimeLoggedIn -> $isFirstTimeLoggedIn")
//            if(isFirstTimeLoggedIn){
//                savedItemsUseCasesWrapper.insertRemoteSavedItemToLocal()
//                addTransactionUseCasesWrapper.insertRemoteTransactionsToLocal()
//                coreUseCasesWrapper.insertUserProfileToLocalDb()
//                budgetUseCaseWrapper.insertRemoteBudgetsToLocal()
//                setupAccountUseCasesWrapper.setFirstTimeLogin(uid = userId)
//                setupAccountUseCasesWrapper.setCurrencyRatesUpdated(isUpdated = false)
//                Log.d("AppEntry","FirstTimeLoggedIn set to false")
//            }
//        } catch (e: Exception) {
//            Log.e("LoadRemoteData", "Failed to sync: ${e.localizedMessage}")
//        }finally {
//            _loginState.value = _loginState.value.copy(isDataSyncing = false)
//        }
//    }

    private suspend fun loadRemoteDataItems() {
        _loginState.value = _loginState.value.copy(isDataSyncing = true)
        try {
            val userId = coreUseCasesWrapper.getUserUIDUseCase() ?: "Unknown"
            val isFirstTimeLoggedIn = setupAccountUseCasesWrapper.getFirstTimeLoggedIn(userId)
            Log.d("AppEntry", "FirstTimeLoggedIn -> $isFirstTimeLoggedIn")
            if (isFirstTimeLoggedIn) {
                Log.d("LoadRemoteData", "Starting insertRemoteSavedItemToLocal")
                savedItemsUseCasesWrapper.insertRemoteSavedItemToLocal()
                Log.d("LoadRemoteData", "Completed insertRemoteSavedItemToLocal")

                Log.d("LoadRemoteData", "Starting insertRemoteTransactionsToLocal")
                addTransactionUseCasesWrapper.insertRemoteTransactionsToLocal()
                Log.d("LoadRemoteData", "Completed insertRemoteTransactionsToLocal")

                Log.d("LoadRemoteData", "Starting insertUserProfileToLocalDb")
                coreUseCasesWrapper.insertUserProfileToLocalDb()
                Log.d("LoadRemoteData", "Completed insertUserProfileToLocalDb")

                Log.d("LoadRemoteData", "Starting insertRemoteBudgetsToLocal")
                budgetUseCaseWrapper.insertRemoteBudgetsToLocal()
                Log.d("LoadRemoteData", "Completed insertRemoteBudgetsToLocal")

                setupAccountUseCasesWrapper.setFirstTimeLogin(uid = userId)
                setupAccountUseCasesWrapper.setCurrencyRatesUpdated(isUpdated = false)
                Log.d("AppEntry", "FirstTimeLoggedIn set to false")
            }
        } catch (e: Exception) {
            Log.e("LoadRemoteData", "Failed to sync: ${e.localizedMessage}")
        } finally {
            _loginState.value = _loginState.value.copy(isDataSyncing = false)
        }
    }



    sealed class LoginEvent{
        data class Success(val username: String): LoginEvent()
        data class Error(val errorMessage: String?): LoginEvent()
        data object TriggerFirebaseLogIn: LoginEvent()
    }
}

