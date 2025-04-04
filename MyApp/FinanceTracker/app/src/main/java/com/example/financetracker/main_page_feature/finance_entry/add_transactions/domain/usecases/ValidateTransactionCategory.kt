package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.auth_feature.domain.usecases.ValidationResult

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