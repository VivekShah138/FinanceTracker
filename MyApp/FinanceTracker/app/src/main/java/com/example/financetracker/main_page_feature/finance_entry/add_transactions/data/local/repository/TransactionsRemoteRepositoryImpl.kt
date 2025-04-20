package com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.repository

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionsEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionDao
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils.DeletedAllTransactionsFromCloudDatabaseWorker
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.utils.UploadAllTransactionsToCloudDatabaseWorker
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.toDomain
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.toEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import com.example.financetracker.main_page_feature.view_records.use_cases.DeleteMultipleTransactionsFromCloud
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class TransactionsRemoteRepositoryImpl(
    private val deletedTransactionDao: DeletedTransactionDao,
    private val context: Context
): TransactionRemoteRepository {
    override suspend fun insertDeletedTransaction(deletedTransactions: DeletedTransactions) {
        return deletedTransactionDao.insertDeletedTransaction(deletedTransactions.toEntity())
    }

    override suspend fun getAllDeletedTransactions(uid: String): Flow<List<DeletedTransactions>> {
        return deletedTransactionDao.getAllDeletedTransactions(uid).map { transactions ->
            transactions.map { transaction ->
                transaction.toDomain()
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