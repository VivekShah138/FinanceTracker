package com.example.financetracker.setup_account.data.local.data_source.country

import androidx.room.TypeConverter
import com.example.financetracker.setup_account.domain.model.Country
import com.example.financetracker.setup_account.domain.model.Currency
import com.example.financetracker.setup_account.domain.model.Flags
import com.example.financetracker.setup_account.domain.model.Idd
import com.example.financetracker.setup_account.domain.model.Name
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CountryMapper {
    private val gson = Gson()

    @TypeConverter
    fun fromIddSuffixes(suffixes: List<String>?): String? {
        return try{
            suffixes?.joinToString(",")
        }catch (e: Exception){
            "N/A ${e.localizedMessage}"
        }
    }

    @TypeConverter
    fun toIddSuffixes(data: String?): List<String>? {
        return try{
            data?.split(",")?.map { it.trim() }
        }catch (e: Exception){
            emptyList()
        }

    }

    @TypeConverter
    fun fromCurrencies(currencies: Map<String, Currency>?): String {
        return try{
            gson.toJson(currencies)
        }catch (e: Exception){
            "N/A ${e.localizedMessage}"
        }

    }

    @TypeConverter
    fun toCurrencies(data: String?): Map<String, Currency> {
        try {
            val type = object : TypeToken<Map<String, Currency>>() {}.type
            return gson.fromJson(data, type)
        }catch (e: Exception){
            return emptyMap()
        }
    }

    fun fromCountryResponseToEntity(response: Country): CountryEntity {
        return CountryEntity(
            commonName = response.name.common ?: "N/A",
            flagUrl = response.flags.svg ?: "N/A",
            iddRoot = response.idd.root ?: "N/A",
            iddSuffixes = fromIddSuffixes(suffixes = response.idd.suffixes),
            currencies = fromCurrencies(response.currencies)
        )
    }

    fun fromEntityToCountryResponse(entity: CountryEntity): Country {
        return Country(
            name = Name(common = entity.commonName),
            flags = Flags(svg = entity.flagUrl),
            idd = Idd(
                root = entity.iddRoot ?: "N/A",
                suffixes = toIddSuffixes(entity.iddSuffixes) ?: emptyList()
            ),
            currencies = toCurrencies(entity.currencies) ?: emptyMap()
        )
    }
}