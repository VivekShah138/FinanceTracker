package com.example.financetracker.setup_account.domain.model

data class Country(
    val name: Name,
    val flags: Flags,
    val idd: Idd,
    val currencies: Map<String, Currency>
)

data class Name(
    val common: String
)

data class Flags(
    val png: String
)

data class Idd(
    val root: String,
    val suffixes: List<String>
)

data class Currency(
    val name: String = "",
    val symbol: String = ""
)



