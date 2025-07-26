package com.example.financetracker.data.local.data_source.room.modules.country

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CountryDao {
    @Upsert
    suspend fun insertAll(countries: List<CountryEntity>)

    @Query("SELECT * FROM CountryEntity")
    suspend fun getAllCountries(): List<CountryEntity>

    @Query("SELECT COUNT(*) FROM CountryEntity")
    suspend fun getCountryCount(): Int
}