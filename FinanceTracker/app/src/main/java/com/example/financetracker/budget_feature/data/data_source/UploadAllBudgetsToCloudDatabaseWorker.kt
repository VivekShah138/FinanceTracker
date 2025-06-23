package com.example.financetracker.budget_feature.data.data_source

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.budget_feature.domain.usecases.BudgetUseCaseWrapper
import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases.AddTransactionUseCasesWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class UploadAllBudgetsToCloudDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferences: UserPreferences,
    private val budgetUseCaseWrapper: BudgetUseCaseWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Log.d("WorkManagerUploadBudgets", "Worker started Upload All Budgets To Cloud")

        val userId = budgetUseCaseWrapper.getUIDLocally() ?: return Result.failure()

        return try {
            val allLocalBudgets = budgetUseCaseWrapper.getAllUnSyncedBudgetLocalUseCase(userId = userId).first()

            Log.d("WorkManagerUploadBudgets", "userId $userId ")
            Log.d("WorkManagerUploadBudgets", "allLocalTransactions $allLocalBudgets")



            val cloudSync = userPreferences.getCloudSync()

            if (allLocalBudgets.isEmpty() || !cloudSync) {
                Log.d("WorkManagerUploadBudgets", "No budgets to sync.")
                Log.d("WorkManagerUploadBudgets", "cloudSync: $cloudSync")
                return Result.failure()

            }
            else{

                allLocalBudgets.forEach { budget ->
                    val budgetId = budget.id
                    val budgetWithId = budget.copy(id = budgetId, cloudSync = true)

                    budgetUseCaseWrapper.saveBudgetToCloudUseCase(userId = userId, budget = budgetWithId)
                    budgetUseCaseWrapper.insertBudgetLocalUseCase(budget = budgetWithId)
                }
                Log.d("WorkManagerUploadBudgets", "All local budgets inserted to cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerUploadBudgets", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}