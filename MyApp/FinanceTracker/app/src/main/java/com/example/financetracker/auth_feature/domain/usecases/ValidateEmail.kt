package com.example.financetracker.auth_feature.domain.usecases


import android.util.Patterns

class ValidateEmail {

    suspend operator fun invoke(email : String) : ValidationResult{
        if(email.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Email"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Valid Email"
            )
        }
        return ValidationResult(
            isSuccessful = true,
        )
    }

}