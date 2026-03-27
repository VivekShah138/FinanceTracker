package com.example.financetracker.domain.usecases.local.currency_rates

import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository

class SeedCurrencyRatesLocalPeriodical(
    private val currencyRatesLocalRepository: CurrencyRatesLocalRepository
) {
    suspend operator fun invoke(){
        return currencyRatesLocalRepository.syncCurrencyRatesPeriodically()
    }
}