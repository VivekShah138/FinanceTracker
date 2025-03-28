package com.example.financetracker.core.cloud.data.repository

import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,

): FirebaseRepository {

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
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
}