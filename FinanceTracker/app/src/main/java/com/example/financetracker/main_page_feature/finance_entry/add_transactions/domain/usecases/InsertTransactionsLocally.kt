package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.domain.repository.local.TransactionLocalRepository
import javax.inject.Inject

class InsertTransactionsLocally @Inject constructor(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(transactions: Transactions){
        transactionLocalRepository.insertTransaction(transactions)
    }
}