package com.example.financetracker.auth_feature.domain.usecases


import android.util.Patterns

class ValidatePhoneNumber {

    suspend operator fun invoke(phone : String) : ValidationResult{

        if(phone.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Mobile Number"
            )
        }
        if(!Patterns.PHONE.matcher(phone).matches()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter a Valid Phone Number"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}