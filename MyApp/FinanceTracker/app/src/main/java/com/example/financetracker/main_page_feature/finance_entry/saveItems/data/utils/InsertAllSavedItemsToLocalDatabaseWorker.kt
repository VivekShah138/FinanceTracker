package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.usecases.SavedItemsUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class InsertAllSavedItemsToLocalDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val savedItemsUseCasesWrapper: SavedItemsUseCasesWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Log.d("WorkManagerInsertToSavedItems", "Worker started Insert All Saved Items To Local Db from Cloud")

        val userId = savedItemsUseCasesWrapper.getUserUIDUseCase() ?: return Result.failure()

        return try {
            val allRemoteSavedItems = savedItemsUseCasesWrapper.getRemoteSavedItemList(userId = userId)

            Log.d("WorkManagerInsertToSavedItems", "userId $userId ")
            Log.d("WorkManagerInsertToSavedItems", "allLocalSavedItems $allRemoteSavedItems")


            if (allRemoteSavedItems.isEmpty()) {
                Log.d("WorkManagerInsertToSavedItems", "No savedItems to Load.")
                return Result.failure()
            }
            else{
                allRemoteSavedItems.forEach { savedItems ->
                    val itemId = savedItems.itemId!!

                    val doesExists = savedItemsUseCasesWrapper.doesItemExistsUseCase(userId = userId,itemId = itemId)

                    if(!doesExists){
                        savedItemsUseCasesWrapper.saveItemLocalUseCase(savedItems)
                        Log.d("WorkManagerInsertToSavedItems", "Remote saved item $itemId inserted to Local Database successfully.")
                    }
                    else{
                        Log.d("WorkManagerInsertToSavedItems", "Remote saved item $itemId already exits in Local Database")
                    }
                }
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerInsertToSavedItems", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}