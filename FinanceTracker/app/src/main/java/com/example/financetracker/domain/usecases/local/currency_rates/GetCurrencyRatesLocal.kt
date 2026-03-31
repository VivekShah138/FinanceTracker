package com.example.financetracker.domain.usecases.local.currency_rates

import com.example.financetracker.domain.model.CurrencyResponse
import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository

class GetCurrencyRatesLocal(
    private val currencyRatesLocalRepository: CurrencyRatesLocalRepository
) {
    suspend operator fun invoke(baseCurrency: String): CurrencyResponse?{
        return currencyRatesLocalRepository.getCurrencyRatesLocal(baseCurrency)
    }
}