package com.example.financetracker.main_page_feature.finance_entry.saveItems.data.repository.remote

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.data_source.DeletedSavedItemsDao
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.DeletedAllSavedItemsFromCloudDatabaseWorker
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.InsertAllSavedItemsToLocalDatabaseWorker
import com.example.financetracker.main_page_feature.finance_entry.saveItems.data.utils.UploadAllSavedItemsToCloudDatabaseWorker
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.DeletedSavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.SavedItems
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.toDomain
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.model.toEntity
import com.example.financetracker.main_page_feature.finance_entry.saveItems.domain.repository.remote.SavedItemsRemoteRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class SavedItemsRemoteRepositoryImpl(
    private val deletedSavedItemsDao: DeletedSavedItemsDao,
    private val firestore: FirebaseFirestore,
    private val context: Context
): SavedItemsRemoteRepository {
    override suspend fun insertDeletedSavedItems(deletedSavedItems: DeletedSavedItems) {
        deletedSavedItemsDao.insertDeletedSavedItems(deletedSavedItems.toEntity())
    }

    override suspend fun getAllDeletedTransactions(uid: String): Flow<List<DeletedSavedItems>> {
        return deletedSavedItemsDao.getAllDeletedSavedItems(uid = uid).map { savedItems ->
            savedItems.map { savedItem ->
                savedItem.toDomain()
            }
        }
    }

    override suspend fun deleteSelectedDeletedTransactionsByIds(itemId: Int) {
        return deletedSavedItemsDao.deleteDeletedSavedItemsByIds(itemId = itemId)
    }

    override suspend fun deleteMultipleSavedItemsFromCloud() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Ensures work runs only when connected
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DeletedAllSavedItemsFromCloudDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "delete_saved_items_from_cloud",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun deletedSingleSavedItemRemote(itemId: Int, userId: String) {
        try {
            val itemIds = itemId.toString()

            firestore.collection("Users")
                .document(userId)
                .collection("SavedItems")
                .document(itemIds)
                .delete()
                .await()

            Log.d("FirestoreDelete", "Deleted SavedItem $itemId from cloud.")

        } catch (e: Exception) {
            Log.e("FirestoreDelete", "Failed to delete saved item from cloud: ${e.localizedMessage}")
            throw e // Optional: rethrow to retry from worker
        }
    }

    override suspend fun cloudSyncSingleSavedItem(
        userId: String,
        savedItems: SavedItems,
        updateCloudSync: suspend (Int, Boolean) -> Unit
    ) {
        try {

            val itemId = savedItems.itemId?.toString() ?: firestore.collection("Users")
                .document(userId)
                .collection("SavedItems")
                .document().id

            firestore.collection("Users")
                .document(userId)
                .collection("SavedItems")
                .document(itemId)
                .set(savedItems.copy(cloudSync = true)) // Save with cloudSync = true
                .await()

            // Update local Room DB
            savedItems.itemId?.let { updateCloudSync(it, true) }

        }catch (e: Exception){

            savedItems.itemId?.let { updateCloudSync(it, false) }
        }
    }

    override suspend fun cloudSyncMultipleSavedItems() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UploadAllSavedItemsToCloudDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "upload_local_saved_items_to_cloud",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun insertRemoteItemToLocal() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<InsertAllSavedItemsToLocalDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "insert_remote_saved_items_to_local",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun getRemoteSavedItems(userId: String): List<SavedItems> {
        return try {
            val snapshot = firestore.collection("Users")
                .document(userId)
                .collection("SavedItems")
                .get(Source.SERVER)
                .await()

            snapshot.documents.mapNotNull { doc ->
                val savedItem = doc.toObject(SavedItems::class.java)
                val id = doc.id.toIntOrNull()
                if (savedItem != null && id != null) {
                    savedItem.copy(itemId = id)
                } else null
            }
        } catch (e: Exception) {
            Log.e("FirestoreGetRemoteSavedItem", "Failed to get savedItems from cloud: ${e.localizedMessage}")
            emptyList()
        }
    }
}