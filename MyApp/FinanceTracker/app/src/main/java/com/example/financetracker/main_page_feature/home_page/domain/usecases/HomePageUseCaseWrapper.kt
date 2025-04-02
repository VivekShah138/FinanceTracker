package com.example.financetracker.main_page_feature.home_page.domain.usecases

import com.example.financetracker.core.core_domain.usecase.LogoutUseCase
import com.example.financetracker.core.local.domain.shared_preferences.usecases.GetCurrencyRatesUpdated
import com.example.financetracker.core.local.domain.shared_preferences.usecases.SetCurrencyRatesUpdated

data class HomePageUseCaseWrapper (
    val logoutUseCase: LogoutUseCase,
    val getUserProfileLocal: GetUserProfileLocal,
    val setCurrencyRatesUpdated: SetCurrencyRatesUpdated,
    val getCurrencyRatesUpdated: GetCurrencyRatesUpdated
)
