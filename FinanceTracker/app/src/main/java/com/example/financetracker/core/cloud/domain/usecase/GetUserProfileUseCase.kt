package com.example.financetracker.core.cloud.domain.usecase

import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(userId: String): UserProfile?{
        return remoteRepository.getUserProfile(userId)
    }
}