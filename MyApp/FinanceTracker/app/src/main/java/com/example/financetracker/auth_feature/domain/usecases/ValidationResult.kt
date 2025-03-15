package com.example.financetracker.auth_feature.domain.usecases


class ValidationResult (
    val isSuccessful : Boolean,
    val errorMessage : String? = null
)