package com.example.financetracker.setup_account.domain.usecases


import com.example.financetracker.auth_feature.domain.usecases.ValidationResult

class ValidatePhoneNumber {

    suspend operator fun invoke(phone : String) : ValidationResult {

        if(phone.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Mobile Number"
            )
        }
        if(!phone.matches(Regex(pattern = "^\\d{10}$"))){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please enter a valid phone number"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}