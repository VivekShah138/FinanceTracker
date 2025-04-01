package com.example.financetracker.setup_account.data.local.data_source

import androidx.room.Dao
import androidx.room.Insert
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