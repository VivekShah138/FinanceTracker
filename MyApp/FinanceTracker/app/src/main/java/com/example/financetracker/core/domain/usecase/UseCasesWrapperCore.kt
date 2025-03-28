package com.example.financetracker.core.domain.usecase

import com.example.financetracker.core.cloud.domain.usecases.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecases.GetUserUIDUseCase
import com.example.financetracker.core.cloud.domain.usecases.SaveUserProfileUseCase
import com.example.financetracker.core.local.domain.shared_preferences.usecases.CheckIsLoggedInUseCase

data class UseCasesWrapperCore(
    val logoutUseCase: LogoutUseCase,
    val checkIsLoggedInUseCase: CheckIsLoggedInUseCase,
    val getUserEmailUserCase: GetUserEmailUserCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val saveUserProfileUseCase: SaveUserProfileUseCase
)