package com.example.financetracker.domain.usecases.local.validation


class ValidateConfirmPassword {

    suspend operator fun invoke(password : String, confirmPassword : String) : ValidationResult {

        if(confirmPassword.length < 8){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password Length Should be at least 8 Digits"
            )
        }

        if(!confirmPassword.equals(other =  password, ignoreCase = false)){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Confirm Password Should Be Same as Password"
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }


}