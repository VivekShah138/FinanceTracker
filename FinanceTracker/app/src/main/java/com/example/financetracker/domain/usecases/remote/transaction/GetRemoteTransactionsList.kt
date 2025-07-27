package com.example.financetracker.domain.usecases.remote.transaction

import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.model.Transactions

class GetRemoteTransactionsList(
    private val remoteRepository: RemoteRepository,
) {

    suspend operator fun invoke(userId: String): List<Transactions>{
        return remoteRepository.getRemoteTransactions(userId = userId)
    }
}