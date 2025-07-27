package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetCurrencyRatesUpdated(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(isUpdated: Boolean){
        return sharedPreferencesRepository.setCurrencyRatesUpdated(isUpdated)
    }
}