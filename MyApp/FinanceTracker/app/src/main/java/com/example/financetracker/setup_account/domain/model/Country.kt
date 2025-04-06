package com.example.financetracker.setup_account.domain.model

import com.example.financetracker.setup_account.data.local.data_source.country.CountryEntity
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper

data class Country(
    val name: Name,
    val flags: Flags,
    val idd: Idd,
    val currencies: Map<String, Currency>
)

data class Name(val common: String)

data class Flags(val svg: String)

data class Idd(val root: String, val suffixes: List<String>)

data class Currency(val name: String = "", val symbol: String = "")

fun Country.toEntity(): CountryEntity{
    return CountryMapper.fromCountryResponseToEntity(this)
}

fun CountryEntity.toDomain(): Country{
    return CountryMapper.fromEntityToCountryResponse(this)
}


