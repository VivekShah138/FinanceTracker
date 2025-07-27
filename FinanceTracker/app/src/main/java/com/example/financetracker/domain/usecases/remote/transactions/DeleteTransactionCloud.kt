package com.example.financetracker.domain.usecases.remote.transactions

import com.example.financetracker.domain.repository.remote.RemoteRepository

class DeleteTransactionCloud(
    private val remoteRepository: RemoteRepository,
) {

    suspend operator fun invoke(userId: String,transactionId: Int){
        remoteRepository.deletedTransactionRemote(transactionId = transactionId,userId = userId)
    }
}