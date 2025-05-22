package com.example.financetracker.core.core_domain.usecase

import com.example.financetracker.core.cloud.domain.usecase.GetUserEmailUserCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserProfileUseCase
import com.example.financetracker.core.cloud.domain.usecase.GetUserUIDUseCase
import com.example.financetracker.core.cloud.domain.usecase.SaveUserProfileUseCase
import com.example.financetracker.core.local.domain.room.usecases.InsertUserProfileToLocalDb
import com.example.financetracker.core.local.domain.shared_preferences.usecases.CheckIsLoggedInUseCase
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetUserNameLocally

data class CoreUseCasesWrapper(
    val logoutUseCase: LogoutUseCase,
    val checkIsLoggedInUseCase: CheckIsLoggedInUseCase,
    val getUserEmailUserCase: GetUserEmailUserCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val saveUserProfileUseCase: SaveUserProfileUseCase,
    val setUserNameLocally: SetUserNameLocally,
    val insertUserProfileToLocalDb: InsertUserProfileToLocalDb
)