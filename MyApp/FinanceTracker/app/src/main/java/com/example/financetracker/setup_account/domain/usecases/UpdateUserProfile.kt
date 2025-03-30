package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import com.example.financetracker.setup_account.domain.model.Currency


class UpdateUserProfile (
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(
        userId: String,
        firstName: String,
        lastName: String,
        email: String,
        baseCurrency: Map<String,Currency>,
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
        return firebaseRepository.saveUserProfile(userId,userProfileUpdated)
    }
}