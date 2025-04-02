package com.example.financetracker.setup_account.domain.repository.local

import com.example.financetracker.setup_account.domain.model.CurrencyResponse

interface CurrencyRatesLocalRepository {
    suspend fun getCurrencyRatesLocal(baseCurrency: String): CurrencyResponse?
    suspend fun insertCurrencyRatesLocalOneTime()
    suspend fun insertCurrencyRatesLocalPeriodically()
}