package com.example.financetracker.auth_feature.domain.usecases

class ValidatePassword {

    suspend operator fun invoke(password : String) : ValidationResult{

        if(password.length < 8){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password Length Should be at least 8 Digits"
            )
        }

        val containsDigitOrLetter = password.any { it.isLetterOrDigit()} && password.any { it.isUpperCase() }

        if(!password.matches(Regex(".*[!@#\$%^&*()_+=<>?/{}~`\\[\\]-].*"))){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password Must Contain at least One Special Character"
            )
        }

        if(!containsDigitOrLetter){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password Must Contain at least One Uppercase Letter Or One Digit"
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }

}