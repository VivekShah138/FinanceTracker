package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.model.Transactions

class GetRemoteTransactionsList(
    private val remoteRepository: RemoteRepository,
) {

    suspend operator fun invoke(userId: String): List<Transactions>{
        return remoteRepository.getRemoteTransactions(userId =  userId)
    }
}