package com.example.financetracker.auth_feature.presentation.forgot_password

interface ResetPasswordWithEmailResult {
    data object EmailSuccess : ResetPasswordWithEmailResult
    data object AuthFailure : ResetPasswordWithEmailResult
    data object Cancelled : ResetPasswordWithEmailResult
    data object UnknownFailure : ResetPasswordWithEmailResult
}