package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetCloudSyncLocalUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(isChecked: Boolean){
        sharedPreferencesRepository.setCloudSync(isChecked)
    }

}