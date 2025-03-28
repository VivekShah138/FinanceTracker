package com.example.financetracker.core.cloud.domain.usecases

import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userId: String): UserProfile?{
        return firebaseRepository.getUserProfile(userId)
    }
}