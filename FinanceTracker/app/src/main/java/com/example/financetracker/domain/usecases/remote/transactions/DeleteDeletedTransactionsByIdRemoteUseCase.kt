package com.example.financetracker.domain.usecases.remote.transactions

import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository

class DeleteDeletedTransactionsByIdRemoteUseCase(
    private val transactionRemoteRepository: TransactionRemoteRepository
) {
    suspend operator fun invoke(transactionId: Int){
        return transactionRemoteRepository.deleteDeletedTransactionById(transactionId = transactionId)
    }
}