package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.domain.repository.local.TransactionLocalRepository

class DoesTransactionExits(
    private val transactionLocalRepository: TransactionLocalRepository
){
    suspend operator fun invoke(userId: String,transactionId: Int): Boolean{
        return transactionLocalRepository.doesTransactionExist(userId = userId,transactionId = transactionId)
    }
}