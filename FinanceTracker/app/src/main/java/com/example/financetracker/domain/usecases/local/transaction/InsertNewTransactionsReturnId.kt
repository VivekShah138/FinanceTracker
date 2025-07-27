package com.example.financetracker.domain.usecases.local.transaction

import com.example.financetracker.domain.model.Transactions
import com.example.financetracker.domain.repository.local.TransactionLocalRepository
import javax.inject.Inject

class InsertNewTransactionsReturnId @Inject constructor(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(transactions: Transactions): Long{
        return transactionLocalRepository.insertTransactionReturningId(transactions = transactions)
    }
}