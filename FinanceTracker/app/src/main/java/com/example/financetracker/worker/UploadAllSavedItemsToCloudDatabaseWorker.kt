package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
import com.example.financetracker.domain.usecases.usecase_wrapper.SavedItemsUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class UploadAllSavedItemsToCloudDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferences: UserPreferences,
    private val savedItemsUseCasesWrapper: SavedItemsUseCasesWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Worker started. WorkId=${id}")
        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {
            val allLocalSavedItems = savedItemsUseCasesWrapper.getAllUnSyncedSavedItemLocalUseCase(userUID = userId).first()

            Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Worker started for userId $userId")
            Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} all local saved items $allLocalSavedItems")

            val cloudSync = userPreferences.getCloudSync()

            if (allLocalSavedItems.isEmpty() || !cloudSync) {
                Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} No saved items to sync.")
                Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} cloudSync: $cloudSync")
                return Result.failure()

            }
            else{
                allLocalSavedItems.forEach { savedItems ->
                    val itemId = savedItems.itemId
                    val savedItemWithId = savedItems.copy(itemId = itemId, cloudSync = true)
                    savedItemsUseCasesWrapper.saveSingleSavedItemCloud(userId = userId,savedItems = savedItemWithId)
                    savedItemsUseCasesWrapper.insertSavedItemLocalUseCase(savedItemWithId)
                    Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Local saved items $itemId inserted to Local Database successfully.")
                }
                Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} All local saved items inserted to cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}