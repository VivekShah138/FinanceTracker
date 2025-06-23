package com.example.financetracker.auth_feature.presentation.register

data class RegisterPageStates (
//    val userName : String = "",
//    val userNameError : String? = null,
    val email : String = "",
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val confirmPassword : String = "",
    val confirmPasswordError : String? = null,
    val errorMessage : String? = null
)