package com.example.financetracker.data.repository.remote

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.Logger
import com.example.financetracker.data.data_source.local.room.modules.saved_items.DeletedSavedItemsDao
import com.example.financetracker.worker.DeletedAllSavedItemsFromCloudDatabaseWorker
import com.example.financetracker.worker.InsertAllSavedItemsToLocalDatabaseWorker
import com.example.financetracker.worker.UploadAllSavedItemsToCloudDatabaseWorker
import com.example.financetracker.domain.model.DeletedSavedItems
import com.example.financetracker.domain.model.SavedItems
import com.example.financetracker.domain.repository.remote.SavedItemsRemoteRepository
import com.example.financetracker.mapper.DeletedSavedItemsMapper
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
        deletedSavedItemsDao.insertDeletedSavedItems(DeletedSavedItemsMapper.toEntity(deletedSavedItems))
    }

    override suspend fun getAllDeletedSavedItems(uid: String): Flow<List<DeletedSavedItems>> {
        return deletedSavedItemsDao.getAllDeletedSavedItems(uid = uid).map { savedItems ->
            savedItems.map { savedItem ->
                DeletedSavedItemsMapper.toDomain(savedItem)
            }
        }
    }

    override suspend fun deleteDeletedSavedItemById(itemId: Int) {
        return deletedSavedItemsDao.deleteDeletedSavedItemsByIds(itemId = itemId)
    }

    override suspend fun deleteMultipleSavedItemsFromCloud() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
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

        Logger.d(Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.DELETE_REMOTE_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} ENQUEUED. WorkId=${workRequest.id}")

    }

    override suspend fun deleteSavedItemRemote(itemId: Int, userId: String) {
        try {
            val itemIds = itemId.toString()

            firestore.collection("Users")
                .document(userId)
                .collection("SavedItems")
                .document(itemIds)
                .delete()
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun syncSavedItem(
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
                .set(savedItems.copy(cloudSync = true))
                .await()

            savedItems.itemId?.let { updateCloudSync(it, true) }

        }catch (e: Exception){
            savedItems.itemId?.let { updateCloudSync(it, false) }
        }
    }

    override suspend fun syncMultipleSavedItemsToRemote() {
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

        Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_REMOTE_WORK_MANAGER} ENQUEUED. WorkId=${workRequest.id}")

    }

    override suspend fun insertSavedItemRemoteToLocal() {
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

        Logger.d(Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER, "${Logger.Tag.INSERT_SAVED_ITEMS_TO_LOCAL_WORK_MANAGER} ENQUEUED. WorkId=${workRequest.id}")
    }

    override suspend fun getSavedItemsRemote(userId: String): List<SavedItems> {
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
            emptyList()
        }
    }
}