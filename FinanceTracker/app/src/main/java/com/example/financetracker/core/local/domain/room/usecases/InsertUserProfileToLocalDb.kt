package com.example.financetracker.core.local.domain.room.usecases

import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository

class InsertUserProfileToLocalDb(
    private val userProfileRepository: UserProfileRepository
) {
    suspend operator fun invoke(userProfile: UserProfile,uid: String) {
        return userProfileRepository.insertUserProfile(userProfile,uid)
    }

    suspend operator fun invoke() {
        return userProfileRepository.insertUserProfile()
    }
}