package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class KeepUserLoggedIn(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke (keepLoggedIn: Boolean){
        return sharedPreferencesRepository.setLoggedInState(keepLoggedIn)
    }
}