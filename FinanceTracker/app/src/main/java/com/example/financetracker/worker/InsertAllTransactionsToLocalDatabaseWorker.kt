package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.AddTransactionUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class InsertAllTransactionsToLocalDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addTransactionUseCasesWrapper: AddTransactionUseCasesWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Worker started. WorkId=${id}")

        val userId = addTransactionUseCasesWrapper.getUserUIDRemoteUseCase() ?: return Result.failure()

        return try {
            val allRemoteTransactions = addTransactionUseCasesWrapper.getAllTransactionsRemoteUseCase(userId = userId)

            Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Worker started for userId $userId ")
            Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} all remote transactions $allRemoteTransactions")


            if (allRemoteTransactions.isEmpty()) {
                Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} No transactions to Load.")
                return Result.failure()
            }
            else{
                allRemoteTransactions.forEach { transaction ->
                    val transactionId = transaction.transactionId!!

                    val doesExists = addTransactionUseCasesWrapper.doesTransactionExitsLocalUseCase(userId = userId,transactionId = transactionId)

                    if(!doesExists){
                        addTransactionUseCasesWrapper.insertTransactionsLocalUseCase(transactions = transaction)
                        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Remote Transaction $transactionId inserted to Local Database successfully.")

                    }
                    else{
                        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_REMOTE_WORK_MANAGER} Remote Transaction $transactionId already exits in Local Database")
                    }
                }
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}