package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class InsertUIDLocalUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    operator fun invoke (userUId: String){
        return sharedPreferencesRepository.setUserIdLocally(userUId)
    }
}