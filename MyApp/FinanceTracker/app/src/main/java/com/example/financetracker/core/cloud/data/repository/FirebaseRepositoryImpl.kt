package com.example.financetracker.core.cloud.data.repository

import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
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
        try {
            // Force a Firestore network call
            firestore.collection("Users").document(userId)
                .get(Source.SERVER) // Ensures Firebase tries fetching from the server
                .await()

            // Now perform the write operation
            firestore.collection("Users").document(userId)
                .set(mapOf("userProfile" to profile), SetOptions.merge())
                .await()

        } catch (e: Exception) {
            throw Exception("No internet connection. Profile update failed.")
        }
    }


    override suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            firestore.collection("Users").document(userId)
                .get(Source.SERVER) // Forces network request, throws exception if offline
                .await()
                .get("userProfile", UserProfile::class.java)
        } catch (e: Exception) {
            throw Exception("No internet connection. Cannot fetch user profile.")
        }
    }

}