package com.example.financetracker.mapper

import com.example.financetracker.data.data_source.local.room.modules.userprofile.UserProfileEntity
import com.example.financetracker.domain.model.UserProfile

object UserProfileMapper {
    fun toEntity(userProfile: UserProfile, uid: String): UserProfileEntity {
        return UserProfileEntity(
            firstName = userProfile.firstName,
            lastName = userProfile.lastName,
            email = userProfile.email,
            baseCurrency = CountryMapper.fromCurrencies(userProfile.baseCurrency),
            country = userProfile.country,
            callingCode = userProfile.callingCode,
            phoneNumber = userProfile.phoneNumber,
            profileSetUpCompleted = userProfile.profileSetUpCompleted,
            uid = uid
        )
    }

    fun toDomain(entity: UserProfileEntity): UserProfile {
        return UserProfile(
            firstName = entity.firstName,
            lastName = entity.lastName,
            email = entity.email,
            baseCurrency = CountryMapper.toCurrencies(entity.baseCurrency) ?: emptyMap(),
            country = entity.country,
            callingCode = entity.callingCode,
            phoneNumber = entity.phoneNumber,
            profileSetUpCompleted = entity.profileSetUpCompleted
        )
    }
}