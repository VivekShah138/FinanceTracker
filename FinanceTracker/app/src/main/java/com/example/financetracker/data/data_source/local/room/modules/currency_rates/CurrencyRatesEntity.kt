package com.example.financetracker.data.data_source.local.room.modules.currency_rates

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRatesEntity(
    @PrimaryKey
    val baseCurrency: String, // The base currency (e.g., INR)
    val rates: String  // JSON representation of the rates map
)
