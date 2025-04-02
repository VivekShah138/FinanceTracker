package com.example.financetracker.setup_account.data.local.data_source.currency_rates

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CurrencyRatesDao {

    @Upsert
    suspend fun insertCurrencyRates(currencyRates: CurrencyRatesEntity)

    @Query("SELECT * FROM CurrencyRatesEntity WHERE baseCurrency = :baseCurrency LIMIT 1")
    suspend fun getCurrencyRates(baseCurrency: String): CurrencyRatesEntity?
}