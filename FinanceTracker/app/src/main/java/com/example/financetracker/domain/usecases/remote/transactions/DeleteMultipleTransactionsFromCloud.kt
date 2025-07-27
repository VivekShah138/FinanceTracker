package com.example.financetracker.domain.usecases.remote.transactions

import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository

class DeleteMultipleTransactionsFromCloud(
    private val transactionRemoteRepository: TransactionRemoteRepository
) {

    suspend operator fun invoke(){
        return transactionRemoteRepository.deleteMultipleTransactionsFromCloud()
    }
}