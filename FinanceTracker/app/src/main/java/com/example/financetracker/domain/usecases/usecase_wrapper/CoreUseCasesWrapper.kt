package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserEmailUserCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserProfileUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.SaveUserProfileUseCase
import com.example.financetracker.domain.usecases.local.user_profile.InsertUserProfileToLocalDb
import com.example.financetracker.domain.usecases.local.shared_pref.CheckIsLoggedInUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetUserNameLocally

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