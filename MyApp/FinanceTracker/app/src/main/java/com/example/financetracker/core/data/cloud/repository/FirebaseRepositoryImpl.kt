package com.example.financetracker.core.data.cloud.repository

import com.example.financetracker.core.data.local.data_source.UserPreferences
import com.example.financetracker.core.domain.model.UserProfile
import com.example.financetracker.core.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
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

    override suspend fun saveUserProfile(userId: String, profile: UserProfile) {
        firestore.collection("Users").document(userId) // Users/userId
            .set(mapOf("userProfile" to profile), SetOptions.merge()) // Stores userProfile as a field
            .await()
    }

    override suspend fun getUserProfile(userId: String): UserProfile? {
        return firestore.collection("Users").document(userId) // Users/userId
            .get().await()
            .get("userProfile", UserProfile::class.java) // Fetch userProfile field as an object
    }

    override fun checkIsLoggedIn(): Boolean {
        return userPreferences.isLoggedIn()
    }

    override fun keepUserLoggedIn(keepLoggedIn: Boolean) {
        return userPreferences.setLoggedInState(keepLoggedIn)
    }
}