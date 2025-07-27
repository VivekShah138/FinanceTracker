package com.example.financetracker.domain.usecases.local.validation

class ValidateTransactionCategory {

    suspend operator fun invoke(category : String) : ValidationResult {
        if(category.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Select The Transaction Category"
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}