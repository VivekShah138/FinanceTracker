package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.repository.local.UserProfileRepository

class GetUserProfileFromLocalDb(
    private val userProfileRepository: UserProfileRepository
) {
    suspend operator fun invoke(uid: String): UserProfile? {
        return userProfileRepository.getUserProfile(uid = uid)
    }
}