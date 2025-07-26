package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class GetFirstTimeLogin(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(uid: String): Boolean{
        return sharedPreferencesRepository.isFirstTimeLoggedIn(uid)
    }

}