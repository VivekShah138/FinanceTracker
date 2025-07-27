package com.example.financetracker.domain.usecases.local.validation


class ValidationResult (
    val isSuccessful : Boolean,
    val errorMessage : String? = null
)