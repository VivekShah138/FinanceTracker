package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.CurrencyResponse

interface CurrencyRatesLocalRepository {
    suspend fun getCurrencyRatesLocal(baseCurrency: String): CurrencyResponse?
    suspend fun syncCurrencyRatesOnce()
    suspend fun syncCurrencyRatesPeriodically()
}