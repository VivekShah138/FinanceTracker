package com.example.financetracker.domain.usecases.remote.transactions

import com.example.financetracker.domain.repository.remote.RemoteRepository

class InsertTransactionsRemoteUseCase(
    private val remoteRepository: RemoteRepository,
) {

    suspend operator fun invoke(){
        return remoteRepository.syncTransactionsRemote()
    }
}