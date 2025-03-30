package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository

class GetUserProfileFromLocalDb(
    private val userProfileRepository: UserProfileRepository
) {
    suspend operator fun invoke(email: String): UserProfile? {
        return userProfileRepository.getUserProfile(email)
    }
}