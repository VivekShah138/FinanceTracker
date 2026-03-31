package com.example.financetracker.domain.usecases.usecase_wrapper

import com.example.financetracker.domain.usecases.remote.user_profile.LogoutUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserEmailRemoteUserCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserProfileRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.GetUserUIDRemoteUseCase
import com.example.financetracker.domain.usecases.remote.user_profile.SaveUserProfileRemoteUseCase
import com.example.financetracker.domain.usecases.local.user_profile.InsertUserProfileLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.CheckIsLoggedInLocalUseCase
import com.example.financetracker.domain.usecases.local.shared_pref.SetUserNameLocalUseCase

data class CoreUseCasesWrapper(
    val logoutUseCase: LogoutUseCase,
    val checkIsLoggedInLocalUseCase: CheckIsLoggedInLocalUseCase,
    val getUserEmailRemoteUserCase: GetUserEmailRemoteUserCase,
    val getUserUIDRemoteUseCase: GetUserUIDRemoteUseCase,
    val getUserProfileRemoteUseCase: GetUserProfileRemoteUseCase,
    val saveUserProfileRemoteUseCase: SaveUserProfileRemoteUseCase,
    val setUserNameLocalUseCase: SetUserNameLocalUseCase,
    val insertUserProfileLocalUseCase: InsertUserProfileLocalUseCase
)