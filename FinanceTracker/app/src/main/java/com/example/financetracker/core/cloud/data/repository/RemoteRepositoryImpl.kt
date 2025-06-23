package com.example.financetracker.core.cloud.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.core.local.domain.room.model.UserProfile
import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils.InsertAllTransactionsToLocalDatabaseWorker
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils.UploadAllTransactionsToCloudDatabaseWorker
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.InsertAllSavedItemsToLocalDatabaseWorker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val context: Context,
    private val workManager: WorkManager

): RemoteRepository {

    override suspend fun logoutUser() {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUserID(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }


    override suspend fun saveUserProfile(userId: String, profile: UserProfile) {
        try {
            // Optional: Check if document exists (forces a server call)
            firestore.collection("Users").document(userId)
                .get(Source.SERVER)
                .await()

            // Overwrite the "userProfile" field completely (no merge)
            firestore.collection("Users").document(userId)
                .set(mapOf("userProfile" to profile)) // No SetOptions.merge()
                .await()

            Log.d("RemoteRepository", "User profile saved successfully")

        } catch (e: Exception) {
            Log.e("RemoteRepository", "Error saving user profile: ${e.localizedMessage}")
            e.printStackTrace()
            throw Exception("No internet connection. Profile update failed.")
        }
    }


    override suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            firestore.collection("Users").document(userId)
                .get(Source.SERVER) // Forces network request, throws exception if offline
                .await()
                .get("userProfile", UserProfile::class.java)
        } catch (e: Exception) {
            Log.d("RemoteRepository","get profile error ${e.localizedMessage}")
            Log.d("RemoteRepository","get profile print stack ${e.printStackTrace()}")
            throw Exception("No internet connection. Cannot fetch user profile.")
        }
    }

    override suspend fun cloudSyncSingleTransaction(
        userId: String,
        transactions: Transactions,
        updateCloudSync: suspend (Int, Boolean) -> Unit
    ) {
        try {

            val transactionId = transactions.transactionId?.toString() ?: firestore.collection("Users")
                .document(userId)
                .collection("Transactions")
                .document().id

            firestore.collection("Users")
                .document(userId)
                .collection("Transactions")
                .document(transactionId)
                .set(transactions.copy(cloudSync = true)) // Save with cloudSync = true
                .await()

            // Update local Room DB
            transactions.transactionId?.let { updateCloudSync(it, true) }

        }catch (e: Exception){

            // If failed, make sure cloudSync remains false
            transactions.transactionId?.let { updateCloudSync(it, false) }
        }
    }

    override suspend fun cloudSyncMultipleTransaction() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Ensures work runs only when connected
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UploadAllTransactionsToCloudDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "upload_local_transactions_to_cloud",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }


    override fun isInternetConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override suspend fun deletedTransactionRemote(transactionId: Int, userId: String) {
        try {
            val transactionDocId = transactionId.toString()

            firestore.collection("Users")
                .document(userId)
                .collection("Transactions")
                .document(transactionDocId)
                .delete()
                .await()

            Log.d("FirestoreDelete", "Deleted transaction $transactionId from cloud.")

        } catch (e: Exception) {
            Log.e("FirestoreDelete", "Failed to delete transaction from cloud: ${e.localizedMessage}")
            throw e // Optional: rethrow to retry from worker
        }
    }

    override suspend fun getRemoteTransactions(userId: String): List<Transactions> {
        return try {
            val snapshot = firestore.collection("Users")
                .document(userId)
                .collection("Transactions")
                .get(Source.SERVER)
                .await()

            snapshot.documents.mapNotNull { doc ->
                val transaction = doc.toObject(Transactions::class.java)
                val id = doc.id.toIntOrNull()
                if (transaction != null && id != null) {
                    transaction.copy(transactionId = id)
                } else null
            }
        } catch (e: Exception) {
            Log.e("FirestoreGetRemoteTransaction", "Failed to get transaction from cloud: ${e.localizedMessage}")
            emptyList()
        }
    }

    override suspend fun insertRemoteTransactionToLocal() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<InsertAllTransactionsToLocalDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "insert_remote_transaction_to_local",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}