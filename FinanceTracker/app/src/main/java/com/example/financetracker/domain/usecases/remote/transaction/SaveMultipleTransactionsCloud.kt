package com.example.financetracker.domain.usecases.remote.transaction

import com.example.financetracker.domain.repository.remote.RemoteRepository

class SaveMultipleTransactionsCloud(
    private val remoteRepository: RemoteRepository,
) {

    suspend operator fun invoke(){
        return remoteRepository.cloudSyncMultipleTransaction()
    }
}