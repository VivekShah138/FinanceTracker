package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class GetUIDLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke (): String?{
        return sharedPreferencesRepository.getUserIdLocally()
    }
}