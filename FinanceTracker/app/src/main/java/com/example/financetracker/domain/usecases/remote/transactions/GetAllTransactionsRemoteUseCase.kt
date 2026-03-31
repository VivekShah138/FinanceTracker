package com.example.financetracker.domain.usecases.remote.transactions

import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.model.Transactions

class GetAllTransactionsRemoteUseCase(
    private val remoteRepository: RemoteRepository,
) {

    suspend operator fun invoke(userId: String): List<Transactions>{
        return remoteRepository.getTransactionsRemote(userId = userId)
    }
}