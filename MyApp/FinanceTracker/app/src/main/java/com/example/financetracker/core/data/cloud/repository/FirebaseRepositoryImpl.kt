package com.example.financetracker.core.data.cloud.repository

import com.example.financetracker.core.data.local.data_source.UserPreferences
import com.example.financetracker.core.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userPreferences: UserPreferences
): FirebaseRepository {

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
        userPreferences.setLoggedInState(false)
    }

    override suspend fun getCurrentUserID(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    override fun checkIsLoggedIn(): Boolean {
        return userPreferences.isLoggedIn()
    }

    override fun keepUserLoggedIn(keepLoggedIn: Boolean) {
        return userPreferences.setLoggedInState(keepLoggedIn)
    }
}