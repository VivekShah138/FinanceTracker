package com.example.financetracker.data.local.repository

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.worker.PrepopulateUserProfileDatabaseWorker
import com.example.financetracker.data.local.data_source.room.modules.userprofile.UserProfileDao
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.local.domain.room.model.toDomain
import com.example.financetracker.core.local.domain.room.model.toEntity
import com.example.financetracker.core.local.domain.room.repository.UserProfileRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val workManager: WorkManager
): UserProfileRepository {
    override suspend fun getUserProfile(uid: String): UserProfile? {
        try {
            // Retrieve the UserProfileEntity from the database using UID
            val userProfileEntity = userProfileDao.getUserProfile(uid)
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

    override suspend fun insertUserProfile(userProfile: UserProfile,uid: String) {
        try {
            val userProfileEntity = userProfile.toEntity(uid)
            Log.d("RoomDatabaseUser", "userProfileEntity $userProfileEntity")
            userProfileDao.insertUserProfile(userProfileEntity)
        }
        catch (e: Exception){
            Log.d("RoomDatabaseUser", "userProfile Exception ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun insertUserProfile() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PrepopulateUserProfileDatabaseWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                30, TimeUnit.SECONDS
            )
            .addTag("PrepopulateUserProfile")
            .build()

        Log.d("WorkManagerCountries", "WorkManager enqueued: $workRequest")
        workManager.enqueueUniqueWork(
            "PrepopulateUserProfile",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}