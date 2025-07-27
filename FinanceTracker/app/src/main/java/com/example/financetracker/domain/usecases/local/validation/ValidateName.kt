package com.example.financetracker.domain.usecases.local.validation


class ValidateName {

    suspend operator fun invoke(name : String) : ValidationResult {

        if(name.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Name"
            )
        }
        if(!name.matches(Regex("^[a-zA-Z ]+\$"))){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Your name should not contain any special characters or digits"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}