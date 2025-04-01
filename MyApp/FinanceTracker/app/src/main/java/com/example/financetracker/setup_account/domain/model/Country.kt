package com.example.financetracker.setup_account.domain.model

import com.example.financetracker.setup_account.data.local.converters.Converters
import com.example.financetracker.setup_account.data.local.data_source.CountryEntity
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken

data class Country(
    val name: Name,
    val flags: Flags,
    val idd: Idd,
    val currencies: Map<String, Currency>
)

data class Name(val common: String)

data class Flags(val png: String)

data class Idd(val root: String, val suffixes: List<String>)

data class Currency(val name: String = "", val symbol: String = "")

fun Country.toEntity(): CountryEntity {

//    val fromIddSuffixes = Converters.fromIddSuffixes()

    return CountryEntity(
        commonName = this.name.common ?: "N/A",
        flagUrl = this.flags.png ?: "N/A",
        iddRoot = this.idd.root ?: "N/A",
//        iddSuffixes = this.idd.suffixes?.joinToString(",") ?: "N/A",  // Safe null check
//        currencies = this.currencies?.let { Gson().toJson(it) } ?: "{}", // Store empty JSON object if null
        iddSuffixes = Converters.fromIddSuffixes(suffixes = this.idd.suffixes),
        currencies = Converters.fromCurrencies(this.currencies)
    )
}


fun CountryEntity.toDomain(): Country {
    return Country(
        name = Name(common = this.commonName),
        flags = Flags(png = this.flagUrl),
//        idd = Idd(
//            root = this.iddRoot ?: "",
//            suffixes = this.iddSuffixes?.split(",") ?: emptyList()
//        ),
//        currencies = parseCurrencies(this.currencies)
        idd = Idd(
            root = this.iddRoot ?: "N/A",
            suffixes = Converters.toIddSuffixes(this.iddSuffixes) ?: emptyList()
        ),
        currencies = Converters.toCurrencies(this.currencies) ?: emptyMap()
    )
}

//private fun parseCurrencies(json: String?): Map<String, Currency> {
//    return try {
//        val type = object : TypeToken<Map<String, Currency>>() {}.type
//        Gson().fromJson(json, type) ?: emptyMap()
//    } catch (e: Exception) {
//        emptyMap()
//    }
//}
