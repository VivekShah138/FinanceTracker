package com.example.financetracker.core.local.domain.room.repository

import com.example.financetracker.core.local.domain.room.model.UserProfile

interface UserProfileRepository {
    suspend fun getUserProfile(email: String): UserProfile?
    suspend fun insertUserProfile(userProfile: UserProfile)
}