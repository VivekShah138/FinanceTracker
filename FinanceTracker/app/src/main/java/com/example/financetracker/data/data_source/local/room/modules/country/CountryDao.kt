package com.example.financetracker.data.data_source.local.room.modules.country

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CountryDao {
    @Upsert
    suspend fun insertAll(countries: List<com.example.financetracker.data.data_source.local.room.modules.country.CountryEntity>)

    @Query("SELECT * FROM CountryEntity")
    suspend fun getAllCountries(): List<com.example.financetracker.data.data_source.local.room.modules.country.CountryEntity>

    @Query("SELECT COUNT(*) FROM CountryEntity")
    suspend fun getCountryCount(): Int
}