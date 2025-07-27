package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.shared_pref.InsertUIDLocally
import com.example.financetracker.domain.usecases.local.validation.ValidateConfirmPassword
import com.example.financetracker.domain.usecases.local.validation.ValidateEmail
import com.example.financetracker.domain.usecases.local.validation.ValidatePassword
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetUIDLocally

data class AuthFeatureUseCasesWrapper(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateConfirmPassword: ValidateConfirmPassword,
    val insertUIDLocally: InsertUIDLocally,
    val getUIDLocally: GetUIDLocally
)