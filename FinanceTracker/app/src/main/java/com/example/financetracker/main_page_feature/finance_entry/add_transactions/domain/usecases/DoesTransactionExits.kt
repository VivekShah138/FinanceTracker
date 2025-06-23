package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.core.local.domain.room.model.Category
import com.example.financetracker.core.local.domain.room.repository.CategoryRepository
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository

class DoesTransactionExits(
    private val transactionLocalRepository: TransactionLocalRepository
){
    suspend operator fun invoke(userId: String,transactionId: Int): Boolean{
        return transactionLocalRepository.doesTransactionExist(userId = userId,transactionId = transactionId)
    }
}