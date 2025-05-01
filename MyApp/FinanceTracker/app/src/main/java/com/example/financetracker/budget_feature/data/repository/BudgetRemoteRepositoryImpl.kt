package com.example.financetracker.budget_feature.data.repository

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.budget_feature.data.data_source.UploadAllBudgetsToCloudDatabaseWorker
import com.example.financetracker.budget_feature.domain.model.Budget
import com.example.financetracker.budget_feature.domain.repository.BudgetRemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils.UploadAllTransactionsToCloudDatabaseWorker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class BudgetRemoteRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val context: Context,
): BudgetRemoteRepository {
    override suspend fun uploadSingleBudgetToCloud(userId: String, budget: Budget) {

        val budgetId = budget.id ?: firestore.collection("Users")
            .document(userId)
            .collection("Budgets")
            .document().id

        firestore.collection("Users")
            .document(userId)
            .collection("Budgets")
            .document(budgetId)
            .set(budget.copy(cloudSync = true)) // Save with cloudSync = true
            .await()
    }

    override suspend fun uploadMultipleBudgetsToCloud() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Ensures work runs only when connected
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UploadAllBudgetsToCloudDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "upload_local_budgets_to_cloud",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}