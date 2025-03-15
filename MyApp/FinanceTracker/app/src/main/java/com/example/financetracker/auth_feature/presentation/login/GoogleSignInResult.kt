package com.example.financetracker.auth_feature.presentation.login

interface GoogleSignInResult {
    data class Success(val username: String): GoogleSignInResult
    data object Cancelled: GoogleSignInResult
    data object Failure: GoogleSignInResult
}