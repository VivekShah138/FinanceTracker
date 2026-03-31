package com.example.financetracker.presentation.features.auth_feature.auth_utils

interface ResetPasswordWithCredentialResult {
    data class CredentialLoginSuccess(val email: String) : ResetPasswordWithCredentialResult
    data object CredentialLoginFailure: ResetPasswordWithCredentialResult
    data object Cancelled : ResetPasswordWithCredentialResult
    data object UnknownFailure : ResetPasswordWithCredentialResult
}