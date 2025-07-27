package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.domain.usecases.local.validation.ValidationResult

class ValidateCountry{

    suspend operator fun invoke(country : String) : ValidationResult {
        if(country.isBlank()){
            return ValidationResult(
                errorMessage = "Please Select a Country",
                isSuccessful = false
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}