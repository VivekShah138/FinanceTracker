package com.example.financetracker.core.cloud.domain.usecases

import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userId: String, userProfile: UserProfile){
        firebaseRepository.saveUserProfile(userId = userId, profile = userProfile)
    }
}