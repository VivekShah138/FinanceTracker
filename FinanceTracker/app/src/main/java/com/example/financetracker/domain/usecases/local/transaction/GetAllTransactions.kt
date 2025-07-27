package com.example.financetracker.domain.usecases.local.transaction

import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.domain.repository.local.TransactionLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllTransactions(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(uid: String): Flow<List<Transactions>>{
        return transactionLocalRepository.getAllTransactions(uid)
    }
}