package com.example.financetracker.core.cloud.domain.usecase

import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.repository.remote.RemoteRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(userId: String): UserProfile?{
        return remoteRepository.getUserProfile(userId)
    }
}