package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository

class SetCloudSyncLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(isChecked: Boolean){
        sharedPreferencesRepository.setCloudSync(isChecked)
    }

}