package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository

class InsertUIDLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke (userUId: String){
        return sharedPreferencesRepository.setUserIdLocally(userUId)
    }
}