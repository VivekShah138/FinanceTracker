package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.domain.model.DeletedTransactions
import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository

class InsertDeletedTransactionsLocally(
    private val transactionRemoteRepository: TransactionRemoteRepository
){

    suspend operator fun invoke(deletedTransactions: DeletedTransactions){
        return transactionRemoteRepository.insertDeletedTransaction(deletedTransactions = deletedTransactions)
    }
}