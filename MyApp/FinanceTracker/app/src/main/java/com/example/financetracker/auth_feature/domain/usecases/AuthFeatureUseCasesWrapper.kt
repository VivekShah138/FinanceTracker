package com.example.financetracker.auth_feature.domain.usecases

data class AuthFeatureUseCasesWrapper(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateConfirmPassword: ValidateConfirmPassword,
    val insertUIDLocally: InsertUIDLocally
)