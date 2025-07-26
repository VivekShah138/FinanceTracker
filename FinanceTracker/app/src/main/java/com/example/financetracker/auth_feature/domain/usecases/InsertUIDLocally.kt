package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class InsertUIDLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke (userUId: String){
        return sharedPreferencesRepository.setUserIdLocally(userUId)
    }
}