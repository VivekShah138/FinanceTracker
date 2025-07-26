package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository

class InsertCurrencyRatesLocalPeriodically(
    private val currencyRatesLocalRepository: CurrencyRatesLocalRepository
) {
    suspend operator fun invoke(){
        return currencyRatesLocalRepository.insertCurrencyRatesLocalPeriodically()
    }
}