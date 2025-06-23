package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class InsertAllTransactionsToLocalDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addTransactionUseCasesWrapper: AddTransactionUseCasesWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Log.d("WorkManagerInsertToTransactions", "Worker started Insert All Transactions To Local Db from Cloud")

        val userId = addTransactionUseCasesWrapper.getUserUIDUseCase() ?: return Result.failure()

        return try {
            val allRemoteTransactions = addTransactionUseCasesWrapper.getRemoteTransactionsList(userId = userId)

            Log.d("WorkManagerInsertToTransactions", "userId $userId ")
            Log.d("WorkManagerInsertToTransactions", "allLocalTransactions $allRemoteTransactions")


            if (allRemoteTransactions.isEmpty()) {
                Log.d("WorkManagerInsertToTransactions", "No transactions to Load.")
                return Result.failure()
            }
            else{
                allRemoteTransactions.forEach { transaction ->
                    val transactionId = transaction.transactionId!!

                    val doesExists = addTransactionUseCasesWrapper.doesTransactionExits(userId = userId,transactionId = transactionId)

                    if(!doesExists){
                        addTransactionUseCasesWrapper.insertTransactionsLocally(transactions = transaction)
                        Log.d("WorkManagerInsertToTransactions", "Remote Transaction $transactionId inserted to Local Database successfully.")
                    }
                    else{
                        Log.d("WorkManagerInsertToTransactions", "Remote Transaction $transactionId already exits in Local Database")
                    }
                }
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerInsertToTransactions", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}