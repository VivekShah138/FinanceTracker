package com.example.financetracker.core.domain.usecases

data class UseCasesWrapperCore(
    val logoutUseCase: LogoutUseCase,
    val checkIsLoggedInUseCase: CheckIsLoggedInUseCase,
    val getUserEmailUserCase: GetUserEmailUserCase,
    val getUserUIDUseCase: GetUserUIDUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val saveUserProfileUseCase: SaveUserProfileUseCase
)