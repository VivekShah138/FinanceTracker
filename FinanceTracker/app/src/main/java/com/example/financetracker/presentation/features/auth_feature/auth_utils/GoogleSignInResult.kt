package com.example.financetracker.presentation.features.auth_feature.auth_utils

interface GoogleSignInResult {
    data class Success(val username: String): GoogleSignInResult
    data object Cancelled: GoogleSignInResult
    data object Failure: GoogleSignInResult
}