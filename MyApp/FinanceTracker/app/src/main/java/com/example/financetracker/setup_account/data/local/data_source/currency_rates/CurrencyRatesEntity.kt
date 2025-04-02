package com.example.financetracker.setup_account.data.local.data_source.currency_rates

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRatesEntity(
    @PrimaryKey
    val baseCurrency: String, // The base currency (e.g., INR)
    val rates: String  // JSON representation of the rates map
)
