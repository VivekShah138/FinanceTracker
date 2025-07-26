package com.example.financetracker.domain.repository.local

import com.example.financetracker.domain.model.UserProfile

interface UserProfileRepository {
    suspend fun getUserProfile(uid: String): UserProfile?
    suspend fun insertUserProfile(userProfile: UserProfile, uid: String)
    suspend fun insertUserProfile()
}