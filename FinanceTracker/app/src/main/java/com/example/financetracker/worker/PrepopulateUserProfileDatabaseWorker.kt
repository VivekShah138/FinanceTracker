package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
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
        Logger.d(Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER} Worker started. WorkId=${id}")


        val userId = coreUseCasesWrapper.getUserUIDRemoteUseCase() ?: return Result.failure()

        Logger.d(Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER} Worker started for userId: $userId")

        return try {

            val userProfile = coreUseCasesWrapper.getUserProfileRemoteUseCase(userId = userId)!!

            Logger.d(Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER} Received UserProfile: $userProfile")

            coreUseCasesWrapper.insertUserProfileLocalUseCase(userProfile = userProfile,uid = userId)

            Logger.d(Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER} UserProfile inserted successfully")

            Result.success()

        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_USER_PROFILE_TO_LOCAL_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}
