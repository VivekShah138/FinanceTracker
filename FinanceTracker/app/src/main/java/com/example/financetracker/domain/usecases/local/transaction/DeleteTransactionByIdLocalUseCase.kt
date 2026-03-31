package com.example.financetracker.domain.usecases.local.transaction

import com.example.financetracker.domain.repository.local.TransactionLocalRepository

class DeleteTransactionByIdLocalUseCase(
    private val transactionLocalRepository: TransactionLocalRepository
){

    suspend operator fun invoke(transactionId: Int){
        return transactionLocalRepository.deleteTransactionsById(transactionId)
    }
}