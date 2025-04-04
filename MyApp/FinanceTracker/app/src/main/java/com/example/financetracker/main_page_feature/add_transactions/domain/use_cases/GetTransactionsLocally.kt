package com.example.financetracker.main_page_feature.add_transactions.domain.use_cases

import com.example.financetracker.main_page_feature.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.add_transactions.domain.repository.TransactionLocalRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionsLocally(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(uid: String): Flow<List<Transactions>>{
        return transactionLocalRepository.getAllTransactions(uid)
    }
}