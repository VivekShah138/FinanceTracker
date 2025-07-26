package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.domain.model.CurrencyResponse
import com.example.financetracker.domain.repository.local.CurrencyRatesLocalRepository

class GetCurrencyRatesLocally(
    private val currencyRatesLocalRepository: CurrencyRatesLocalRepository
) {
    suspend operator fun invoke(baseCurrency: String): CurrencyResponse?{
        return currencyRatesLocalRepository.getCurrencyRatesLocal(baseCurrency)
    }
}