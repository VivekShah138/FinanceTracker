package com.example.financetracker.data.local.data_source.room.modules.country

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    @PrimaryKey val commonName: String,
    val flagUrl: String,
    val iddRoot: String?,
    val iddSuffixes: String?, // Stored as a comma-separated string
    val currencies: String? // Stored as JSON or a comma-separated string
)
