package com.example.financetracker.domain.usecases.local.validation


class ValidateEmail {

    suspend operator fun invoke(email : String) : ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Email"
            )
        }
        if(!isValidEmail(email)){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Valid Email"
            )
        }
        return ValidationResult(
            isSuccessful = true,
        )
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        return emailRegex.matches(email)
    }

}