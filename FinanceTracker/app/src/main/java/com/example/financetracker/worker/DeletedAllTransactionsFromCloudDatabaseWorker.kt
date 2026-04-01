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
class DeletedAllTransactionsFromCloudDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferences: UserPreferences,
    private val viewRecordsUseCaseWrapper: ViewRecordsUseCaseWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Worker started. WorkId=${id}")
        val userId = userPreferences.getUserIdLocally() ?: return Result.failure()

        return try {
            Logger.d(Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Worker started for UserId: $userId")

            val allDeletedTransactions = viewRecordsUseCaseWrapper.getAllDeletedTransactionByUIDUseCase(userId = userId).first()

            Logger.d(Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} All deletedTransactions $allDeletedTransactions")

            if (allDeletedTransactions.isEmpty() ) {
                Logger.d(Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} No deleted transactions to sync.")
                return Result.success()

            }
            else{
                allDeletedTransactions.forEach { deletedTransaction ->
                    viewRecordsUseCaseWrapper.deleteTransactionRemoteUseCase(userId = userId, transactionId = deletedTransaction.transactionId)
                    viewRecordsUseCaseWrapper.deleteDeletedTransactionsByIdRemoteUseCase(transactionId = deletedTransaction.transactionId)
                }
                Logger.d(Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} All pending transactions deleted from cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.DELETE_REMOTE_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}