package com.example.financetracker.domain.usecases.remote.transaction

import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.domain.repository.local.TransactionLocalRepository

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