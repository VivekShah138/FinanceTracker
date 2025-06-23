package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository

class SetCurrencyRatesUpdated(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(isUpdated: Boolean){
        return sharedPreferencesRepository.setCurrencyRatesUpdated(isUpdated)
    }
}