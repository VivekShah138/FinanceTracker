package com.example.financetracker.domain.usecases.local.transaction

import com.example.financetracker.domain.repository.local.TransactionLocalRepository

class DeleteSelectedTransactionsByIdsLocally(
    private val transactionLocalRepository: TransactionLocalRepository
){

    suspend operator fun invoke(transactionId: Int){
        return transactionLocalRepository.deleteSelectedTransactionsByIds(transactionId)
    }
}