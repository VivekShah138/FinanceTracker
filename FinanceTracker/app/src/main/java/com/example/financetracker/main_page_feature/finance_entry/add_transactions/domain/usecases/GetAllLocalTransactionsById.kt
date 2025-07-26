package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.domain.repository.local.TransactionLocalRepository

class GetAllLocalTransactionsById(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(transactionId: Int): Transactions {
        return transactionLocalRepository.getAllLocalTransactionsById(transactionId)
    }
}