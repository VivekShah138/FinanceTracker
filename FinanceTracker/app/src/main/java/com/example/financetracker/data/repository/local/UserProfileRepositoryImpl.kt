package com.example.financetracker.data.repository.local

import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.room.modules.userprofile.UserProfileDao
import com.example.financetracker.worker.PrepopulateUserProfileDatabaseWorker
import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.repository.local.UserProfileRepository
import com.example.financetracker.mapper.UserProfileMapper
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val workManager: WorkManager
): UserProfileRepository {
    override suspend fun getUserProfile(uid: String): UserProfile {
        try {
            val userProfileEntity = userProfileDao.getUserProfile(uid)

            val userProfile = UserProfileMapper.toDomain(userProfileEntity)
            return userProfile
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun insertUserProfile(userProfile: UserProfile, uid: String) {
        try {
            val userProfileEntity = UserProfileMapper.toEntity(userProfile = userProfile,uid = uid)
            userProfileDao.insertUserProfile(userProfileEntity)
        }
        catch (e: Exception){
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


        workManager.enqueueUniqueWork(
            "PrepopulateUserProfile",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
        Logger.d(Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER} One time already enqueued.")
    }
}