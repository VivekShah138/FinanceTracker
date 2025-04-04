package com.example.financetracker.main_page_feature.add_transactions.expense.domain.usecases

import com.example.financetracker.auth_feature.domain.usecases.ValidationResult

class ValidateTransactionQuantity {

    suspend operator fun invoke(quantity : String) : ValidationResult {
        if(quantity.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Transaction Quantity"
            )
        }

        return ValidationResult(
            isSuccessful = true,
        )
    }
}