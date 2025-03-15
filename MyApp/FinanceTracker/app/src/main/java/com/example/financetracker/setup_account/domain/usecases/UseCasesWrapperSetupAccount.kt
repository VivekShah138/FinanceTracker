package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.core.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.domain.usecases.GetUserUIDUseCase

data class UseCasesWrapperSetupAccount(
    val getUserEmailUserCase: GetUserEmailUserCase,
    val getUserUIDUseCase: GetUserUIDUseCase
)