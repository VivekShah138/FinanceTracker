package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
import com.example.financetracker.domain.usecases.usecase_wrapper.AddTransactionUseCasesWrapper
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
        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Worker started. WorkId=${id}")

        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {
            val allLocalTransactions = addTransactionUseCasesWrapper.getAllUnsyncedTransactionsLocalUseCase(uid = userId).first()

            Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Worker started for userId $userId")
            Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} all local transactions items $allLocalTransactions")

            val cloudSync = userPreferences.getCloudSync()

            if (allLocalTransactions.isEmpty() || !cloudSync) {
                Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} No transactions to sync.")
                Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} cloudSync: $cloudSync")
                return Result.failure()
            }
            else{
                allLocalTransactions.forEach { transaction ->
                    val transactionId = transaction.transactionId
                    val transactionWithId = transaction.copy(transactionId = transactionId, cloudSync = true)
                    addTransactionUseCasesWrapper.insertSingleTransactionRemoteUseCase(userId = userId, transaction)
                    addTransactionUseCasesWrapper.insertTransactionsLocalUseCase(transactionWithId)
                    Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Local transaction $transactionId inserted to Local Database successfully.")
                }
                Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} All local transactions inserted to cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}