package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.auth_feature.domain.usecases.ValidationResult

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