package com.example.financetracker.core.local.data.room.repository

import android.util.Log
import androidx.room.RoomDatabase
import com.example.financetracker.core.local.data.room.data_source.userprofile.UserProfileDao
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.local.domain.room.model.toDomain
import com.example.financetracker.core.local.domain.room.model.toEntity
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository
import javax.inject.Inject


class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
): UserProfileRepository {
    override suspend fun getUserProfile(email: String): UserProfile? {
        try {
            // Retrieve the UserProfileEntity from the database using UID
            val userProfileEntity = userProfileDao.getUserProfile(email)
            Log.d("RoomDatabaseUser", "userProfileEntity $userProfileEntity")
            if (userProfileEntity == null) {
                Log.d("RoomDatabaseUser", "userProfile is null")
            }

            // If an entity is found, convert it to a domain model and return it
            val userProfile = userProfileEntity?.toDomain()
            Log.d("RoomDatabaseUser", "userProfile $userProfile")
            return userProfile
        } catch (e: Exception) {
            Log.d("RoomDatabaseUser", "userProfile Exception ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun insertUserProfile(userProfile: UserProfile) {
        try {
            val userProfileEntity = userProfile.toEntity()
            Log.d("RoomDatabaseUser", "userProfileEntity $userProfileEntity")
            userProfileDao.insertUserProfile(userProfileEntity)
        }
        catch (e: Exception){
            Log.d("RoomDatabaseUser", "userProfile Exception ${e.localizedMessage}")
            throw e
        }
    }
}