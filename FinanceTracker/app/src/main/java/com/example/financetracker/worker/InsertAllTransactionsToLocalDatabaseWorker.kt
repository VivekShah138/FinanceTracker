package com.example.financetracker.worker

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financetracker.Logger
import com.example.financetracker.domain.model.Category
import com.example.financetracker.domain.usecases.local.category.GetPredefinedCategoriesLocalUseCase
import com.example.financetracker.domain.usecases.usecase_wrapper.AddTransactionUseCasesWrapper
import com.example.financetracker.domain.usecases.usecase_wrapper.PredefinedCategoriesUseCaseWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class InsertAllTransactionsToLocalDatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addTransactionUseCasesWrapper: AddTransactionUseCasesWrapper,
    private val predefinedCategoriesUseCaseWrapper: PredefinedCategoriesUseCaseWrapper
) : CoroutineWorker(context, workerParams)  {

    override suspend fun doWork(): Result {
        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} Worker started. WorkId=${id}")

        val userId = addTransactionUseCasesWrapper.getUserUIDRemoteUseCase() ?: return Result.failure()

        return try {
            val allRemoteTransactions = addTransactionUseCasesWrapper.getAllTransactionsRemoteUseCase(userId = userId)

            Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} Worker started for userId $userId ")
            Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} all remote transactions $allRemoteTransactions")


            if (allRemoteTransactions.isEmpty()) {
                Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} No transactions to Load.")
                return Result.failure()
            }
            else{

                // Convert the category list to category name list
                val predefinedCategories = predefinedCategoriesUseCaseWrapper
                    .getPredefinedCategoriesLocalUseCase()
                    .first()
                    .map { it.name }
                    .toMutableSet()

                Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} predefined categories: $predefinedCategories")


                allRemoteTransactions.forEach { transaction ->
                    val transactionId = transaction.transactionId!!
                    val doesExists = addTransactionUseCasesWrapper.doesTransactionExitsLocalUseCase(userId = userId,transactionId = transactionId)

                    if (!predefinedCategories.contains(transaction.category)) {

                        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} transaction category for transaction id $transactionId is ${transaction.category}")

                        val category = Category(
                            uid = userId,
                            name = transaction.category,
                            type = transaction.transactionType.lowercase(),
                            isCustom = true,
                            icon = "ic_custom"
                        )

                        predefinedCategoriesUseCaseWrapper.insertCustomCategoriesLocalUseCase(category)
                        predefinedCategories.add(transaction.category)

                        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} new category ${category.name} successfully installed")
                    }

                    if(!doesExists){
                        addTransactionUseCasesWrapper.insertTransactionsLocalUseCase(transactions = transaction)
                        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} Remote Transaction $transactionId inserted to Local Database successfully.")
                    }
                    else{
                        Logger.d(Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_TRANSACTIONS_TO_LOCAL_WORK_MANAGER} Remote Transaction $transactionId already exits in Local Database")
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