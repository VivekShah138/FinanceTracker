package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils

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
class DeletedAllTransactionsFromCloudDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferences: UserPreferences,
    private val viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Log.d("WorkManager", "Worker started Deleted All Transactions From Cloud")

        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {

            Log.d("WorkManager","UserId: $userId")
            val allDeletedTransactions = viewRecordsUseCaseWrapper.getAllDeletedTransactionByUserId(userId = userId).first()

            Log.d("WorkManager","deletedTransactions $allDeletedTransactions")


            if (allDeletedTransactions.isEmpty() ) {
                Log.d("WorkManager", "No deleted transactions to sync.")
                return Result.success()

            }
            else{
                allDeletedTransactions.forEach { deletedTransaction ->
                    viewRecordsUseCaseWrapper.deleteTransactionCloud(userId = userId, transactionId = deletedTransaction.transactionId)
                    viewRecordsUseCaseWrapper.deleteDeletedTransactionsByIdsFromLocal(transactionId = deletedTransaction.transactionId)
                }
                Log.d("WorkManager", "All Cloud transactions deleted from cloud successfully.")


                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManager", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry() // This will schedule a retry with backoff
        }
    }
}