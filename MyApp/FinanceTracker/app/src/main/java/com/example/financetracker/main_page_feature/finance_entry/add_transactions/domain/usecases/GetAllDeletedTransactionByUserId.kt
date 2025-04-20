package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.DeletedTransactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllDeletedTransactionByUserId(
    private val transactionRemoteRepository: TransactionRemoteRepository
) {
    suspend operator fun invoke(userId: String): Flow<List<DeletedTransactions>>{
        return transactionRemoteRepository.getAllDeletedTransactions(uid = userId)
    }
}