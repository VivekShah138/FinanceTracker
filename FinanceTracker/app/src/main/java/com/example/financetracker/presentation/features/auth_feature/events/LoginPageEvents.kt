package com.example.financetracker.presentation.features.auth_feature.events

import com.example.financetracker.presentation.features.auth_feature.auth_utils.GoogleSignInResult
import com.example.financetracker.presentation.features.auth_feature.auth_utils.ResetPasswordWithCredentialResult

sealed class LoginPageEvents{
    data class ChangeEmail(val email: String) : LoginPageEvents()
    data class ChangePassword(val password: String) : LoginPageEvents()
    data object SubmitLogIn: LoginPageEvents()
    data class ClickLoginWithGoogle(val result: GoogleSignInResult) : LoginPageEvents()
    data class LoginSuccess(val userName: String): LoginPageEvents()
    data class LoginFailure(val error: String): LoginPageEvents()
    data class ClickForgotPassword(val result: ResetPasswordWithCredentialResult): LoginPageEvents()
    data class SetLoadingTrue(val state: Boolean): LoginPageEvents()
}
