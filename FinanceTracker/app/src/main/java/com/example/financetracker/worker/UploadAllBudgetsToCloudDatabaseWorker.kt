package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.BudgetUseCaseWrapper
import com.example.financetracker.data.data_source.local.shared_pref.UserPreferences
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
        Logger.d(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} Worker started. WorkId=${id}")
        val userId = budgetUseCaseWrapper.getUIDLocalUseCase() ?: return Result.failure()

        return try {
            val allLocalBudgets = budgetUseCaseWrapper.getAllUnSyncedBudgetLocalUseCase(userId = userId).first()
            Logger.d(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} Worker started for userId $userId ")
            Logger.d(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} all local budgets $allLocalBudgets")


            val cloudSync = userPreferences.getCloudSync()

            if (allLocalBudgets.isEmpty() || !cloudSync) {
                Logger.d(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} No budgets to sync.")
                Logger.d(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} cloudSync: $cloudSync")
                return Result.failure()
            }
            else{
                allLocalBudgets.forEach { budget ->
                    val budgetId = budget.id
                    val budgetWithId = budget.copy(id = budgetId, cloudSync = true)
                    budgetUseCaseWrapper.insertBudgetRemoteUseCase(userId = userId, budget = budgetWithId)
                    budgetUseCaseWrapper.insertBudgetLocalUseCase(budget = budgetWithId)
                    Logger.d(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} Local budget $budgetId inserted to Local Database successfully.")
                }
                Logger.d(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} All local budgets inserted to cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER,"${Logger.Tag.INSERT_BUDGETS_TO_REMOTE_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}