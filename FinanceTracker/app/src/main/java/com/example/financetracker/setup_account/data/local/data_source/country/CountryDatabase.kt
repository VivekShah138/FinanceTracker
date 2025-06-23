package com.example.financetracker.setup_account.data.local.data_source.country

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CountryEntity::class],
    version = 1
)
abstract class CountryDatabase: RoomDatabase() {
    abstract val countryDao: CountryDao

    companion object{
        const val DATABASE_NAME = "country_db"
    }
}