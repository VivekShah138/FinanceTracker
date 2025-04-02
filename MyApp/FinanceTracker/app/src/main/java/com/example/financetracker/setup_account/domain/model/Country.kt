package com.example.financetracker.setup_account.domain.model

import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.data.local.data_source.country.CountryEntity

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


    return CountryEntity(
        commonName = this.name.common ?: "N/A",
        flagUrl = this.flags.png ?: "N/A",
        iddRoot = this.idd.root ?: "N/A",
        iddSuffixes = CountryMapper.fromIddSuffixes(suffixes = this.idd.suffixes),
        currencies = CountryMapper.fromCurrencies(this.currencies)
    )
}


fun CountryEntity.toDomain(): Country {
    return Country(
        name = Name(common = this.commonName),
        flags = Flags(png = this.flagUrl),
        idd = Idd(
            root = this.iddRoot ?: "N/A",
            suffixes = CountryMapper.toIddSuffixes(this.iddSuffixes) ?: emptyList()
        ),
        currencies = CountryMapper.toCurrencies(this.currencies) ?: emptyMap()
    )
}

