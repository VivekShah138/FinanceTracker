package com.example.financetracker.presentation.features.auth_feature.auth_utils

interface RegisterResult {
    data object Success: RegisterResult
    data object CredentialCancelled: RegisterResult
    data object CredentialFailure: RegisterResult
    data object RegistrationFailure: RegisterResult
    data object FirebaseAuthUserCollisionException: RegisterResult
    data object UnknownFailure: RegisterResult
}