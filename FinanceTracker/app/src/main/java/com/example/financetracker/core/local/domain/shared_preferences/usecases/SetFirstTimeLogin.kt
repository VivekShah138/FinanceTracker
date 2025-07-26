package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetFirstTimeLogin(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(uid: String){
        return sharedPreferencesRepository.setFirstTimeLoggedIn(uid)
    }

}