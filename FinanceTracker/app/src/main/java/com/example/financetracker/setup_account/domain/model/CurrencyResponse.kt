package com.example.financetracker.setup_account.domain.model

data class CurrencyResponse(
    val base_code: String,
    val conversion_rates: Map<String, Double>
)

