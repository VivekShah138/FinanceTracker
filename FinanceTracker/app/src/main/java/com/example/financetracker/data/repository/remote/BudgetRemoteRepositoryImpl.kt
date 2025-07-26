package com.example.financetracker.data.repository.remote

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.worker.InsertAllBudgetsToLocalDatabaseWorker
import com.example.financetracker.worker.UploadAllBudgetsToCloudDatabaseWorker
import com.example.financetracker.domain.model.Budget
import com.example.financetracker.domain.repository.remote.BudgetRemoteRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
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

    override suspend fun getRemoteBudget(userId: String): List<Budget> {
        return try {
            val snapshot = firestore.collection("Users")
                .document(userId)
                .collection("Budgets")
                .get(Source.SERVER)
                .await()

            snapshot.documents.mapNotNull { doc ->
                val budget = doc.toObject(Budget::class.java)
                budget?.copy(id = budget.id)
            }
        } catch (e: Exception) {
            Log.e("FirestoreGetRemoteBudget", "Failed to get Budget from cloud: ${e.localizedMessage}")
            emptyList()
        }
    }

    override suspend fun insertRemoteBudgetToLocal() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<InsertAllBudgetsToLocalDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "insert_remote_budget_to_local",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}