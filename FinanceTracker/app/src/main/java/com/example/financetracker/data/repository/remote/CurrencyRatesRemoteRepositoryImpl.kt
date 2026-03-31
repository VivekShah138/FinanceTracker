package com.example.financetracker.data.repository.remote

import com.example.financetracker.data.data_source.remote.CurrencyRatesApi
import com.example.financetracker.domain.model.CurrencyResponse
import com.example.financetracker.domain.repository.remote.CurrencyRatesRemoteRepository
import javax.inject.Inject

class CurrencyRatesRemoteRepositoryImpl @Inject constructor(
    private val api: CurrencyRatesApi
): CurrencyRatesRemoteRepository {
    override suspend fun getCurrencyRates(baseCurrency: String): CurrencyResponse? {
        return api.getExchangeRates(baseCurrency = baseCurrency)
    }
}