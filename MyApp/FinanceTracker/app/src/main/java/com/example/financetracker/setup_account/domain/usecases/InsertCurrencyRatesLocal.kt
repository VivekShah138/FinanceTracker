package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository
import com.example.financetracker.setup_account.domain.repository.local.CurrencyRatesLocalRepository

class InsertCurrencyRatesLocal(
    private val currencyRatesLocalRepository: CurrencyRatesLocalRepository
) {
    suspend operator fun invoke(){
        return currencyRatesLocalRepository.insertCurrencyRatesLocal()
    }
}