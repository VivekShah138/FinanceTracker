package com.example.financetracker.core.local.domain.room.model

import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileEntity
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.domain.model.Currency
import com.google.gson.Gson

data class UserProfile(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val baseCurrency: Map<String,Currency> = emptyMap(),
    val country: String = "",
    val callingCode: String = "",
    val phoneNumber: String = "",
    val profileSetUpCompleted: Boolean = false
)

fun UserProfile.toEntity(uid: String): UserProfileEntity {
    return UserProfileEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
//        baseCurrency = Gson().toJson(this.baseCurrency), // Serialize Map to JSON string
        baseCurrency = CountryMapper.fromCurrencies(this.baseCurrency), // Serialize Map to JSON string
        country = this.country,
        callingCode = this.callingCode,
        phoneNumber = this.phoneNumber,
        profileSetUpCompleted = this.profileSetUpCompleted,
        uid = uid
    )
}

fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
//        baseCurrency = Gson().fromJson(
//            this.baseCurrency,
//            Map::class.java
//        ) as Map<String, Currency>, // Deserialize JSON string back to Map
        baseCurrency =  CountryMapper.toCurrencies(this.baseCurrency) ?: emptyMap(),
        country = this.country,
        callingCode = this.callingCode,
        phoneNumber = this.phoneNumber,
        profileSetUpCompleted = this.profileSetUpCompleted
    )
}
