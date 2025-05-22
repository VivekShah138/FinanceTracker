package com.example.financetracker.budget_feature.data.data_source

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class InsertAllBudgetsToLocalDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val budgetUseCaseWrapper: BudgetUseCaseWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Log.d("WorkManagerInsertToBudgets", "Worker started Insert All Budgets To Local Db from Cloud")

        val userId = budgetUseCaseWrapper.getUserUIDUseCase() ?: return Result.failure()

        return try {
            val allRemoteBudgets = budgetUseCaseWrapper.getRemoteBudgetsList(userId = userId)

            Log.d("WorkManagerInsertToBudgets", "userId $userId ")
            Log.d("WorkManagerInsertToBudgets", "allLocalTransactions $allRemoteBudgets")


            if (allRemoteBudgets.isEmpty()) {
                Log.d("WorkManagerInsertToBudgets", "No budget to Load.")
                return Result.failure()
            }
            else{
                allRemoteBudgets.forEach { budget ->
                    val budgetId = budget.id

                    val doesExists = budgetUseCaseWrapper.doesBudgetExits(userId = userId,id = budgetId)

                    if(!doesExists){
                        budgetUseCaseWrapper.insertBudgetLocalUseCase(budget = budget)
                        Log.d("WorkManagerInsertToBudgets", "Remote Budget $budgetId inserted to Local Database successfully.")
                    }
                    else{
                        Log.d("WorkManagerInsertToBudgets", "Remote Budget $budgetId already exits in Local Database")
                    }
                }
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerInsertToBudgets", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}