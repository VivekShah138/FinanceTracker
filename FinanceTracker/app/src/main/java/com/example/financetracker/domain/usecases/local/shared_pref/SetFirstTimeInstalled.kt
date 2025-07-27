package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetFirstTimeInstalled(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(){
        return sharedPreferencesRepository.setFirstTimeInstalled()
    }

}