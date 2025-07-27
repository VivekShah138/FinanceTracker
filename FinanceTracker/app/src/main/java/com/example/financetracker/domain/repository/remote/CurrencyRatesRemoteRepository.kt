package com.example.financetracker.domain.repository.remote

import com.example.financetracker.domain.model.CurrencyResponse

interface CurrencyRatesRemoteRepository {
    suspend fun getCurrencyRates(baseCurrency: String): CurrencyResponse?
}