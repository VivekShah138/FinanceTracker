package com.example.financetracker.auth_feature.presentation.forgot_password

interface ResetPasswordWithCredentialResult {
    data class CredentialLoginSuccess(val email: String) : ResetPasswordWithCredentialResult
    data object CredentialLoginFailure: ResetPasswordWithCredentialResult
    data object Cancelled : ResetPasswordWithCredentialResult
    data object UnknownFailure : ResetPasswordWithCredentialResult
}