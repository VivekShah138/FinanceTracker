package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository

class GetDarkModeLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(): Boolean{
        return sharedPreferencesRepository.getDarkMode()
    }

}