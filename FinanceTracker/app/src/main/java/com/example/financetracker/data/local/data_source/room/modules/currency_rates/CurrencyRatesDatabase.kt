package com.example.financetracker.data.local.data_source.room.modules.currency_rates

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CurrencyRatesEntity::class],
    version = 1
)
abstract class CurrencyRatesDatabase: RoomDatabase(){
    abstract val currencyRatesDao : CurrencyRatesDao

    companion object{
        const val DATABASE_NAME = "currency_rates"
    }
}