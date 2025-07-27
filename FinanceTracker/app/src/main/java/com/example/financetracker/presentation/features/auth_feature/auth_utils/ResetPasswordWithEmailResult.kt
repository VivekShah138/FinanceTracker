package com.example.financetracker.presentation.features.auth_feature.auth_utils

interface ResetPasswordWithEmailResult {
    data object EmailSuccess : ResetPasswordWithEmailResult
    data object AuthFailure : ResetPasswordWithEmailResult
    data object Cancelled : ResetPasswordWithEmailResult
    data object UnknownFailure : ResetPasswordWithEmailResult
}