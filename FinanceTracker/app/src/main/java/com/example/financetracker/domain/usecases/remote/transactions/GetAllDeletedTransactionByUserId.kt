package com.example.financetracker.domain.usecases.remote.transactions

import com.example.financetracker.domain.model.DeletedTransactions
import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllDeletedTransactionByUserId(
    private val transactionRemoteRepository: TransactionRemoteRepository
) {
    suspend operator fun invoke(userId: String): Flow<List<DeletedTransactions>>{
        return transactionRemoteRepository.getAllDeletedTransactions(uid = userId)
    }
}