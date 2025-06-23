package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
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
        Log.d("WorkManagerUploadSavedItems", "Worker started Upload All Saved Items To Cloud")

        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {
            val allLocalSavedItems = savedItemsUseCasesWrapper.getAllNotSyncedSavedItemUseCase(userUID = userId).first()

            Log.d("WorkManagerUploadSavedItems", "userId $userId ")
            Log.d("WorkManagerUploadSavedItems", "allLocalSavedItems $allLocalSavedItems")



            val cloudSync = userPreferences.getCloudSync()

            if (allLocalSavedItems.isEmpty() || !cloudSync) {
                Log.d("WorkManagerUploadSavedItems", "No savedItems to sync.")
                Log.d("WorkManagerUploadSavedItems", "cloudSync: $cloudSync")
                return Result.failure()

            }
            else{

                allLocalSavedItems.forEach { savedItems ->
                    val itemId = savedItems.itemId
                    val savedItemWithId = savedItems.copy(itemId = itemId, cloudSync = true)

                    savedItemsUseCasesWrapper.saveSingleSavedItemCloud(userId = userId,savedItems = savedItemWithId)
                    savedItemsUseCasesWrapper.saveItemLocalUseCase(savedItemWithId)

                }
                Log.d("WorkManagerUploadSavedItems", "All local saved items inserted to cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerUploadSavedItems", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}