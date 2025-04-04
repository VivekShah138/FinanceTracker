package com.example.financetracker.setup_account.data.local.data_source.currency_rates

import com.example.financetracker.setup_account.domain.model.CurrencyResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CurrencyRatesMapper {
    private val gson = Gson()

    fun fromCurrencyResponseToEntity(response: CurrencyResponse): CurrencyRatesEntity {
        val ratesJson = gson.toJson(response.conversion_rates)
        return CurrencyRatesEntity(
            baseCurrency = response.base_code,
            rates = ratesJson
        )
    }

    fun fromEntityToCurrencyResponse(entity: CurrencyRatesEntity): CurrencyResponse {
        val type = object : TypeToken<Map<String, Double>>() {}.type
        val ratesMap: Map<String, Double> = gson.fromJson(entity.rates, type)
        return CurrencyResponse(
            base_code = entity.baseCurrency,
            conversion_rates = ratesMap
        )
    }
}