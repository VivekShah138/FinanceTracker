package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.domain.usecases.usecase_wrapper.CoreUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PrepopulateUserProfileDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val coreUseCasesWrapper: CoreUseCasesWrapper

) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("WorkManagerUserProfile", "Worker started")

        val userId = coreUseCasesWrapper.getUserUIDUseCase() ?: return Result.failure()

        return try {
            Log.d("WorkManagerUserProfile", "Fetching UserProfile Remotely...")

            val userProfile = coreUseCasesWrapper.getUserProfileUseCase(userId = userId)!!

            Log.d("WorkManagerUserProfile", "Received UserProfile: $userProfile")


            Log.d("WorkManagerUserProfile", "Inserting userProfile into Room...")
            coreUseCasesWrapper.insertUserProfileToLocalDb(userProfile = userProfile,uid = userId)

            Log.d("WorkManagerUserProfile", "UserProfile inserted successfully")
            Result.success()

        } catch (e: Exception) {
            Log.e("WorkManagerUserProfile", "Unexpected error occurred", e)
            Result.failure()
        }
    }
}
