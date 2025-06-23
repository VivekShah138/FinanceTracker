package com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases

import com.example.financetracker.auth_feature.domain.usecases.ValidationResult

class SavedItemsValidationUseCase{

    suspend operator fun invoke(
        state: String,
        stateName: String
    ): ValidationResult{
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