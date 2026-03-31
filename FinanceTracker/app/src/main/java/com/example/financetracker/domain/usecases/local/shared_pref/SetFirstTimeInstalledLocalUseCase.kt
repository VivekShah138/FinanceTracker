package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetFirstTimeInstalledLocalUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(){
        return sharedPreferencesRepository.setFirstTimeInstalled()
    }

}