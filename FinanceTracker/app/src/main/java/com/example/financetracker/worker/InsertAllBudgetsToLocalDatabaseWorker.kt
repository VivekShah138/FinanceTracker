package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.domain.usecases.usecase_wrapper.BudgetUseCaseWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class InsertAllBudgetsToLocalDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val budgetUseCaseWrapper: BudgetUseCaseWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER} Worker started. WorkId=${id}")

        val userId = budgetUseCaseWrapper.getUserUIDRemoteUseCase() ?: return Result.failure()

        return try {
            val allRemoteBudgets = budgetUseCaseWrapper.getBudgetsRemoteUseCase(userId = userId)
            Logger.d(Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER} Worker started for userId $userId")
            Logger.d(Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER} All remote budgets $allRemoteBudgets")

            if (allRemoteBudgets.isEmpty()) {
                Logger.d(Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER} No budget to sync.")
                return Result.failure()
            }
            else{
                allRemoteBudgets.forEach { budget ->
                    val budgetId = budget.id

                    val doesExists = budgetUseCaseWrapper.doesBudgetExitsLocalUseCase(userId = userId,id = budgetId)

                    if(!doesExists){
                        budgetUseCaseWrapper.insertBudgetLocalUseCase(budget = budget)
                        Logger.d(Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER} Remote Budget $budgetId inserted to Local Database successfully.")
                    }
                    else{
                        Logger.d(Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER} Remote Budget $budgetId already exits in Local Database.")
                    }
                }
                Result.success()
            }
        } catch (e: Exception) {
            Logger.e(Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER,"${Logger.Tag.INSERT_BUDGETS_TO_LOCAL_WORK_MANAGER} Error during sync",e)
            Result.retry()
        }
    }
}