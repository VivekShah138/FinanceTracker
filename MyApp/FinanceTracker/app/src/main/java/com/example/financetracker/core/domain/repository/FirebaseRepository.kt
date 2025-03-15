package com.example.financetracker.core.domain.repository

interface FirebaseRepository {
    suspend fun logoutUser()
    suspend fun getCurrentUserID(): String?
    suspend fun getCurrentUserEmail(): String?
    fun checkIsLoggedIn(): Boolean
    fun keepUserLoggedIn(keepLoggedIn: Boolean)
}