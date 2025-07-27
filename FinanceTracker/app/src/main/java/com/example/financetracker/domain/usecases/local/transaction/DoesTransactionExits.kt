package com.example.financetracker.domain.usecases.local.transaction

import com.example.financetracker.domain.repository.local.TransactionLocalRepository

class DoesTransactionExits(
    private val transactionLocalRepository: TransactionLocalRepository
){
    suspend operator fun invoke(userId: String,transactionId: Int): Boolean{
        return transactionLocalRepository.doesTransactionExist(userId = userId,transactionId = transactionId)
    }
}