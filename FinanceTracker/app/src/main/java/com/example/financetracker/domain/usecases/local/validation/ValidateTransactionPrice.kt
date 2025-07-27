package com.example.financetracker.domain.usecases.local.validation

class ValidateTransactionPrice {

    suspend operator fun invoke(price : String) : ValidationResult {
        if(price.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Transaction Price"
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}