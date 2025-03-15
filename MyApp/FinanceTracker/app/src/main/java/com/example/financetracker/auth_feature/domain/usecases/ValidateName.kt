package com.example.financetracker.auth_feature.domain.usecases


class ValidateName {

    suspend operator fun invoke(name : String) : ValidationResult{

        if(name.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please Enter The Name"
            )
        }
        if(!name.matches(Regex("^[a-zA-Z][a-zA-Z0-9]*\$"))){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Your Username Should Begin With an Alphabet And No Special Characters Are Allowed"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}