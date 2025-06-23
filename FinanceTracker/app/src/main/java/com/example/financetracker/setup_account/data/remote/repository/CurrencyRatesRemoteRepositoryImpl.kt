package com.example.financetracker.setup_account.data.remote.repository

import com.example.financetracker.setup_account.data.remote.CurrencyRatesApi
import com.example.financetracker.setup_account.domain.model.CurrencyResponse
import com.example.financetracker.setup_account.domain.repository.remote.CurrencyRatesRemoteRepository
import javax.inject.Inject

class CurrencyRatesRemoteRepositoryImpl @Inject constructor(
    private val api: CurrencyRatesApi
): CurrencyRatesRemoteRepository {
    override suspend fun getCurrencyRates(baseCurrency: String): CurrencyResponse? {
        return api.getExchangeRates(baseCurrency = baseCurrency)
    }
}