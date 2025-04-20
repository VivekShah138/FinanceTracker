package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository

class SaveSingleTransactionCloud(
    private val remoteRepository: RemoteRepository,
    private val transactionLocalRepository: TransactionLocalRepository
) {

    suspend operator fun invoke(userId: String,transactions: Transactions){
        remoteRepository.cloudSyncSingleTransaction(
            userId = userId,
            transactions = transactions,
            updateCloudSync = {id,status ->
                transactionLocalRepository.updateCloudSyncStatus(id,status)
            }
        )
    }
}