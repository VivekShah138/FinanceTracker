package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.SavedItemsUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class InsertAllSavedItemsToLocalDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val savedItemsUseCasesWrapper: SavedItemsUseCasesWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Worker started. WorkId=${id}")

        val userId = savedItemsUseCasesWrapper.getUserUIDRemoteUseCase() ?: return Result.failure()

        return try {
            val allRemoteSavedItems = savedItemsUseCasesWrapper.getRemoteSavedItemList(userId = userId)

            Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Worker started for userId $userId")
            Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} All remote saved items $allRemoteSavedItems")

            if (allRemoteSavedItems.isEmpty()) {
                Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} No savedItems to Load.")
                return Result.failure()
            }
            else{
                allRemoteSavedItems.forEach { savedItems ->
                    val itemId = savedItems.itemId!!
                    val doesExists = savedItemsUseCasesWrapper.doesSavedItemExistsUseCase(userId = userId,itemId = itemId)

                    if(!doesExists){
                        savedItemsUseCasesWrapper.insertSavedItemLocalUseCase(savedItems)
                        Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Remote saved item $itemId inserted to local database successfully.")
                    }
                    else{
                        Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} Remote saved item $itemId already exits in Local Database.")
                    }
                }
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}