package com.example.financetracker.data.repository.remote

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.data.data_source.local.room.modules.transactions.DeletedTransactionDao
import com.example.financetracker.worker.DeletedAllTransactionsFromCloudDatabaseWorker
import com.example.financetracker.domain.model.DeletedTransactions
import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository
import com.example.financetracker.mapper.DeletedTransactionMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class TransactionsRemoteRepositoryImpl(
    private val deletedTransactionDao: DeletedTransactionDao,
    private val context: Context
): TransactionRemoteRepository {
    override suspend fun insertDeletedTransaction(deletedTransactions: DeletedTransactions) {
        return deletedTransactionDao.insertDeletedTransaction(DeletedTransactionMapper.toEntity(deletedTransactions))
    }

    override suspend fun getAllDeletedTransactions(uid: String): Flow<List<DeletedTransactions>> {
        return deletedTransactionDao.getAllDeletedTransactions(uid).map { transactions ->
            transactions.map { transaction ->
                DeletedTransactionMapper.fromEntity(transaction)
            }
        }
    }

    override suspend fun deleteSelectedDeletedTransactionsByIds(transactionId: Int) {
        return deletedTransactionDao.deleteSelectedDeletedTransactionsByIds(transactionId)
    }

    override suspend fun deleteMultipleTransactionsFromCloud() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Ensures work runs only when connected
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DeletedAllTransactionsFromCloudDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "delete_transactions_from_cloud",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

}