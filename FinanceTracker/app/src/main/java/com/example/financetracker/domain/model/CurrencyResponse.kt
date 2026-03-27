package com.example.financetracker.domain.model

data class CurrencyResponse(
    val baseCode: String,
    val conversionRates: Map<String, Double>
)

