package com.example.financetracker.presentation.features.auth_feature.events

sealed class ForgotPasswordEvents{
    data class ChangeEmail(val email: String): ForgotPasswordEvents()
    data object Submit: ForgotPasswordEvents()
    data object EmailSendSuccess: ForgotPasswordEvents()
    data class EmailSendFailure(val errorMessage: String): ForgotPasswordEvents()
}