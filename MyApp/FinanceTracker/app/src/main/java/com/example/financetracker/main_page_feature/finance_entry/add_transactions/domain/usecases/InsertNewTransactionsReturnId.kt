package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import javax.inject.Inject

class InsertNewTransactionsReturnId @Inject constructor(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(transactions: Transactions): Long{
        return transactionLocalRepository.insertTransactionReturningId(transactions = transactions)
    }
}