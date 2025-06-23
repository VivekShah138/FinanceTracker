package com.example.financetracker.auth_feature.presentation.forgot_password

sealed class ForgotPasswordEvents{
    data class ChangeEmail(val email: String): ForgotPasswordEvents()
    data object Submit: ForgotPasswordEvents()
    data object EmailSendSuccess: ForgotPasswordEvents()
    data class EmailSendFailure(val errorMessage: String): ForgotPasswordEvents()
}