package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import javax.inject.Inject

class CheckIsLoggedInUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(): Boolean{
        return sharedPreferencesRepository.checkIsLoggedIn()
    }
}