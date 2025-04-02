package com.example.financetracker.main_page_feature.home_page.domain.usecases

import com.example.financetracker.core.core_domain.usecase.LogoutUseCase

data class HomePageUseCaseWrapper (
    val logoutUseCase: LogoutUseCase,
    val getUserProfileLocal: GetUserProfileLocal
)
