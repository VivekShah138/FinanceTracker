package com.example.financetracker.data.data_source.local.room.modules.currency_rates

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CurrencyRatesDao {

    @Upsert
    suspend fun insertCurrencyRates(currencyRates: com.example.financetracker.data.data_source.local.room.modules.currency_rates.CurrencyRatesEntity)

    @Query("SELECT * FROM CurrencyRatesEntity WHERE baseCurrency = :baseCurrency LIMIT 1")
    suspend fun getCurrencyRates(baseCurrency: String): com.example.financetracker.data.data_source.local.room.modules.currency_rates.CurrencyRatesEntity?
}