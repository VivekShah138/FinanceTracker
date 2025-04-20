package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionRemoteRepository

class DeleteDeletedTransactionsByIdsFromLocal(
    private val transactionRemoteRepository: TransactionRemoteRepository
) {
    suspend operator fun invoke(transactionId: Int){
        return transactionRemoteRepository.deleteSelectedDeletedTransactionsByIds(transactionId = transactionId)
    }
}