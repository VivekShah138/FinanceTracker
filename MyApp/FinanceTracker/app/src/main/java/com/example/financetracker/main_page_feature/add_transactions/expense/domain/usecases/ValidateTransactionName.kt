package com.example.financetracker.main_page_feature.add_transactions.expense.domain.usecases

import android.util.Patterns
import com.example.financetracker.auth_feature.domain.usecases.ValidationResult

class ValidateTransactionName {

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