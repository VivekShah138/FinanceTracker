package com.example.financetracker.setup_account.domain.usecases

import com.example.financetracker.core.domain.model.UserProfile
import com.example.financetracker.core.domain.repository.FirebaseRepository


class UpdateUserProfile (
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(
        userId: String,
        firstName: String,
        lastName: String,
        email: String,
        baseCurrency: String,
        country: String,
        callingCode: String ,
        phoneNumber: String ,
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