package com.example.financetracker.domain.usecases.local.transaction

import com.example.financetracker.domain.model.DeletedTransactions
import com.example.financetracker.domain.repository.remote.TransactionRemoteRepository

class InsertDeletedTransactionsLocally(
    private val transactionRemoteRepository: TransactionRemoteRepository
){

    suspend operator fun invoke(deletedTransactions: DeletedTransactions){
        return transactionRemoteRepository.insertDeletedTransaction(deletedTransactions = deletedTransactions)
    }
}