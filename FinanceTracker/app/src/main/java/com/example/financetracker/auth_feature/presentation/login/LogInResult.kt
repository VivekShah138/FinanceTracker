package com.example.financetracker.auth_feature.presentation.login

interface LogInResult {
    data class Success(val userName: String): LogInResult
    data object Failure: LogInResult
}