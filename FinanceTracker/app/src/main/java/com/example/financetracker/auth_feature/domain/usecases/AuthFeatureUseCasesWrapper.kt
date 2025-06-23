package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally

data class AuthFeatureUseCasesWrapper(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateConfirmPassword: ValidateConfirmPassword,
    val insertUIDLocally: InsertUIDLocally,
    val getUIDLocally: GetUIDLocally
)