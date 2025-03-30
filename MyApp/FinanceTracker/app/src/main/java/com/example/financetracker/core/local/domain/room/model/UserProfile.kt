package com.example.financetracker.core.local.domain.room.model

import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileEntity
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

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        baseCurrency = Gson().toJson(this.baseCurrency), // Serialize Map to JSON string
        country = this.country,
        callingCode = this.callingCode,
        phoneNumber = this.phoneNumber,
        profileSetUpCompleted = this.profileSetUpCompleted
    )
}

fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        baseCurrency = Gson().fromJson(
            this.baseCurrency,
            Map::class.java
        ) as Map<String, Currency>, // Deserialize JSON string back to Map
        country = this.country,
        callingCode = this.callingCode,
        phoneNumber = this.phoneNumber,
        profileSetUpCompleted = this.profileSetUpCompleted
    )
}
