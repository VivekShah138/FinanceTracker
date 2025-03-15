package com.example.financetracker.auth_feature.domain.usecases

data class UseCasesWrapper(
    val validateEmail: ValidateEmail,
    val validateName: ValidateName,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validatePassword: ValidatePassword,
    val validateConfirmPassword: ValidateConfirmPassword,
    val keepUserLoggedIn: KeepUserLoggedIn
)