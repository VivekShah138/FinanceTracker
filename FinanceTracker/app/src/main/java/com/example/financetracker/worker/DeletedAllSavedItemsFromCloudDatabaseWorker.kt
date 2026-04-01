package com.example.financetracker.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
import com.example.financetracker.domain.usecases.usecase_wrapper.ViewRecordsUseCaseWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DeletedAllSavedItemsFromCloudDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferences: UserPreferences,
    private val viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Worker started. WorkId=${id}")

        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {
            Logger.d(Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Worker started for UserId: $userId")
            val allDeletedSavedItems = viewRecordsUseCaseWrapper.getAllDeletedSavedItemsByUserIdUseCase(userId = userId).first()

            Logger.d(Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} All deletedSavedItems $allDeletedSavedItems")

            if (allDeletedSavedItems.isEmpty() ) {
                Logger.d(Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} No deleted saved items to sync.")
                return Result.success()
            }
            else{
                allDeletedSavedItems.forEach { deletedSavedItems ->
                    viewRecordsUseCaseWrapper.deleteSavedItemCloud(userId = userId, itemId = deletedSavedItems.itemId ?: 0)
                    viewRecordsUseCaseWrapper.deleteDeletedSavedItemByIdUseCase(itemId = deletedSavedItems.itemId ?: 0)
                }
                Logger.d(Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} All Cloud saved items deleted from cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}