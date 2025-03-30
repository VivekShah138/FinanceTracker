package com.example.financetracker.setup_account.data.local.converters

import androidx.room.TypeConverter
import com.example.financetracker.setup_account.domain.model.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIddSuffixes(suffixes: List<String>?): String? {
        return suffixes?.joinToString(",")
    }

    @TypeConverter
    fun toIddSuffixes(data: String?): List<String>? {
        return data?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun fromCurrencies(currencies: Map<String, Currency>?): String? {
        return gson.toJson(currencies)
    }

    @TypeConverter
    fun toCurrencies(data: String?): Map<String, Currency>? {
        val type = object : TypeToken<Map<String, Currency>>() {}.type
        return gson.fromJson(data, type)
    }
}