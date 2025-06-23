package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class UploadAllTransactionsToCloudDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferences: UserPreferences,
    private val addTransactionUseCasesWrapper: AddTransactionUseCasesWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Log.d("WorkManagerUploadTransactions", "Worker started Upload All Transactions To Cloud")

        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {
            val allLocalTransactions = addTransactionUseCasesWrapper.getAllLocalTransactions(uid = userId).first()

            Log.d("WorkManagerUploadTransactions", "userId $userId ")
            Log.d("WorkManagerUploadTransactions", "allLocalTransactions $allLocalTransactions")



            val cloudSync = userPreferences.getCloudSync()

            if (allLocalTransactions.isEmpty() || !cloudSync) {
                Log.d("WorkManagerUploadTransactions", "No transactions to sync.")
                Log.d("WorkManagerUploadTransactions", "cloudSync: $cloudSync")
                return Result.failure()

            }
            else{

                allLocalTransactions.forEach { transaction ->
                    val transactionId = transaction.transactionId
                    val transactionWithId = transaction.copy(transactionId = transactionId, cloudSync = true)

                    addTransactionUseCasesWrapper.saveSingleTransactionCloud(userId = userId, transaction)
                    addTransactionUseCasesWrapper.insertTransactionsLocally(transactionWithId)
                }
                Log.d("WorkManagerUploadTransactions", "All local transactions inserted to cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerUploadTransactions", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}