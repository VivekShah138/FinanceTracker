package com.example.financetracker.setup_account.data.local.repository

import android.util.Log
import com.example.financetracker.setup_account.data.local.data_source.CountryDao
import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.model.toDomain
import com.example.financetracker.setup_account.domain.model.toEntity
import com.example.financetracker.setup_account.domain.repository.local.CountryLocalRepository
import javax.inject.Inject

class CountryLocalRepositoryImpl @Inject constructor(
    private val countryDao: CountryDao
): CountryLocalRepository {
    override suspend fun getCountries(): List<Country> {
        val countries = countryDao.getAllCountries().map { it.toDomain() }
        Log.d("RoomDatabase", "Fetched Countries from Room: $countries")
        return countries
    }

    override suspend fun insertCountries(countries: List<Country>) {
        try {
//            Log.d("RoomDatabase", "Received countries to insert: $countries")

            val countryEntities = countries.map { country ->
                try {
                    val entity = country.toEntity()
//                    Log.d("RoomDatabase", "Mapped to Entity: $entity")
                    entity
                } catch (e: Exception) {
//                    Log.e("RoomDatabase", "Error mapping country: $country, Error: ${e.message}")
                    throw e  // Rethrow to catch in the outer try-catch
                }
            }

//            Log.d("RoomDatabase", "Inserting Countries into Room: $countryEntities")

            countryDao.insertAll(countryEntities)

            // Fetch back the inserted data to verify
            val insertedData = countryDao.getAllCountries()
//            Log.d("RoomDatabase", "Retrieved After Insertion: $insertedData")

        } catch (e: Exception) {
//            Log.e("RoomDatabase", "Error inserting countries: ${e.message}")
        }
    }


}