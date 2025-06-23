package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository

class GetUserNameLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(): String?{
        return sharedPreferencesRepository.getUserName()
    }

}