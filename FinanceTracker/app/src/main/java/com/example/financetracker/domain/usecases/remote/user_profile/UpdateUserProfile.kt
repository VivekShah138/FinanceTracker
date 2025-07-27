package com.example.financetracker.domain.usecases.remote.user_profile

import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.model.Currency


class UpdateUserProfile (
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(
        userId: String,
        firstName: String,
        lastName: String,
        email: String,
        baseCurrency: Map<String, Currency>,
        country: String,
        callingCode: String,
        phoneNumber: String,
        isProfileSetupComplete: Boolean = true
    ) {
        val userProfileUpdated = UserProfile(
            firstName = firstName,
            lastName = lastName,
            email = email,
            baseCurrency = baseCurrency,
            country = country,
            callingCode = callingCode,
            phoneNumber = phoneNumber,
            profileSetUpCompleted = isProfileSetupComplete
        )
        return remoteRepository.saveUserProfile(userId,userProfileUpdated)
    }
}