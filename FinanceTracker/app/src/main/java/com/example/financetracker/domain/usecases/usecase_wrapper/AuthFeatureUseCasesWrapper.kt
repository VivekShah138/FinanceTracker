package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.local.shared_pref.InsertUIDLocalUseCase
import com.example.financetracker.domain.usecases.local.validation.ConfirmPasswordValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.EmailValidationUseCase
import com.example.financetracker.domain.usecases.local.validation.PasswordValidationUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.GetUIDLocalUseCase

data class AuthFeatureUseCasesWrapper(
    val emailValidationUseCase: EmailValidationUseCase,
    val passwordValidationUseCase: PasswordValidationUseCase,
    val confirmPasswordValidationUseCase: ConfirmPasswordValidationUseCase,
    val insertUIDLocalUseCase: InsertUIDLocalUseCase,
    val getUIDLocalUseCase: GetUIDLocalUseCase
)