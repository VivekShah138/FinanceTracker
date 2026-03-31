package com.example.financetracker.domain.usecases.local.validation

class TransactionPriceValidationUseCase {

    operator fun invoke(price : String) : ValidationResult {
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