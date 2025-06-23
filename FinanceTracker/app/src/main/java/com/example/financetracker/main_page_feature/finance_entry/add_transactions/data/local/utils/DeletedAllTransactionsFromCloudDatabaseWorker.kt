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
        Log.d("WorkManagerDeletedTransactions", "Worker started Deleted All Transactions From Cloud")

        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {

            Log.d("WorkManagerDeletedTransactions","UserId: $userId")
            val allDeletedTransactions = viewRecordsUseCaseWrapper.getAllDeletedTransactionByUserId(userId = userId).first()

            Log.d("WorkManagerDeletedTransactions","deletedTransactions $allDeletedTransactions")


            if (allDeletedTransactions.isEmpty() ) {
                Log.d("WorkManagerDeletedTransactions", "No deleted transactions to sync.")
                return Result.success()

            }
            else{
                allDeletedTransactions.forEach { deletedTransaction ->
                    viewRecordsUseCaseWrapper.deleteTransactionCloud(userId = userId, transactionId = deletedTransaction.transactionId)
                    viewRecordsUseCaseWrapper.deleteDeletedTransactionsByIdsFromLocal(transactionId = deletedTransaction.transactionId)
                }
                Log.d("WorkManagerDeletedTransactions", "All Cloud transactions deleted from cloud successfully.")


                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerDeletedTransactions", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry() // This will schedule a retry with backoff
        }
    }
}