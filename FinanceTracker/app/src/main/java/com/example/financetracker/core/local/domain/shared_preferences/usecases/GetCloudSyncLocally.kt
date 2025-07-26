package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class GetCloudSyncLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(): Boolean{
        return sharedPreferencesRepository.getCloudSync()
    }

}