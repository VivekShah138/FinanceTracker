package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class KeepUserLoggedIn(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke (keepLoggedIn: Boolean){
        return sharedPreferencesRepository.setLoggedInState(keepLoggedIn)
    }
}