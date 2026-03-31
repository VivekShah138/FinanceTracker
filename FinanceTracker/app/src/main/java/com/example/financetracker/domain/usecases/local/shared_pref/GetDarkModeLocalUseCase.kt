package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class GetDarkModeLocalUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(): Boolean{
        return sharedPreferencesRepository.getDarkMode()
    }

}