package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.auth_feature.domain.usecases.ValidationResult

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