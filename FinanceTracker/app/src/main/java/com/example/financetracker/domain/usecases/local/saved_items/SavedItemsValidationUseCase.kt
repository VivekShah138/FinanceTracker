package com.example.financetracker.domain.usecases.local.saved_items

import com.example.financetracker.domain.usecases.local.validation.ValidationResult

class SavedItemsValidationUseCase{

    suspend operator fun invoke(
        state: String,
        stateName: String
    ): ValidationResult {
        if(state.isEmpty()){
            return ValidationResult(
                errorMessage = "$stateName cannot be empty",
                isSuccessful = false
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}