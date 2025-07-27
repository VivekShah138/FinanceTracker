package com.example.financetracker.domain.usecases.local.validation

class ValidateEmptyField {

    suspend operator fun invoke(name : String) : ValidationResult {
        if(name.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Transaction Name"
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}