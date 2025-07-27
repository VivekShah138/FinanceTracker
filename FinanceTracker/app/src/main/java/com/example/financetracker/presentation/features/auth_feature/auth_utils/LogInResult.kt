package com.example.financetracker.presentation.features.auth_feature.auth_utils

interface LogInResult {
    data class Success(val userName: String): LogInResult
    data object Failure: LogInResult
}