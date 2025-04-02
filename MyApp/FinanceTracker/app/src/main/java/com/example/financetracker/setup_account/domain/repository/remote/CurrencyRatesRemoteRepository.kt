package com.example.financetracker.setup_account.domain.repository.remote

import com.example.financetracker.setup_account.domain.model.CurrencyResponse

interface CurrencyRatesRemoteRepository {
    suspend fun getCurrencyRates(baseCurrency: String): CurrencyResponse?
}