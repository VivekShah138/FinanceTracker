package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository

class DeleteMultipleTransactionsFromCloud(
    private val transactionRemoteRepository: TransactionRemoteRepository
) {

    suspend operator fun invoke(){
        return transactionRemoteRepository.deleteMultipleTransactionsFromCloud()
    }
}