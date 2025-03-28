package com.example.financetracker.core.domain.repository

import com.example.financetracker.core.domain.model.UserProfile

interface FirebaseRepository {
    suspend fun logoutUser()
    suspend fun getCurrentUserID(): String?
    suspend fun getCurrentUserEmail(): String?
    suspend fun saveUserProfile(userId: String,profile: UserProfile)
    suspend fun getUserProfile(userId: String): UserProfile?
    fun checkIsLoggedIn(): Boolean
    fun keepUserLoggedIn(keepLoggedIn: Boolean)
}