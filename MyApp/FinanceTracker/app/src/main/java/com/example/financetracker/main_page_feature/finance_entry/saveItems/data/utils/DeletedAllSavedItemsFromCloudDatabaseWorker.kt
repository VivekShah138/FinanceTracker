package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import com.example.financetracker.main_page_feature.view_records.use_cases.ViewRecordsUseCaseWrapper
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
        Log.d("WorkManagerDeletedSavedItems", "Worker started Deleted All SavedItems From Cloud")

        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {

            Log.d("WorkManagerDeletedSavedItems","UserId: $userId")
            val allDeletedSavedItems = viewRecordsUseCaseWrapper.getAllDeletedSavedItemsByUserId(userId = userId).first()

            Log.d("WorkManagerDeletedSavedItems","deletedSavedItems $allDeletedSavedItems")


            if (allDeletedSavedItems.isEmpty() ) {
                Log.d("WorkManagerDeletedSavedItems", "No deleted saved items to sync.")
                return Result.success()

            }
            else{
                allDeletedSavedItems.forEach { deletedSavedItems ->
                    viewRecordsUseCaseWrapper.deleteSavedItemCloud(userId = userId, itemId = deletedSavedItems.itemId ?: 0)
                    viewRecordsUseCaseWrapper.deleteDeletedSavedItemsById(itemId = deletedSavedItems.itemId ?: 0)
                }
                Log.d("WorkManagerDeletedSavedItems", "All Cloud saved items deleted from cloud successfully.")


                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerDeletedSavedItems", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry() // This will schedule a retry with backoff
        }
    }
}