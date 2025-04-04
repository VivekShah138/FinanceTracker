package com.example.financetracker.main_page_feature.add_transactions.domain.use_cases

import com.example.financetracker.main_page_feature.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.add_transactions.domain.repository.TransactionLocalRepository
import javax.inject.Inject

class InsertTransactionsLocally @Inject constructor(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(transactions: Transactions){
        transactionLocalRepository.insertTransaction(transactions)
    }
}