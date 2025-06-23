package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository

class KeepUserLoggedIn(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke (keepLoggedIn: Boolean){
        return sharedPreferencesRepository.setLoggedInState(keepLoggedIn)
    }
}