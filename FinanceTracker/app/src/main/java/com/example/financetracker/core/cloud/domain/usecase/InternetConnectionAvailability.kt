package com.example.financetracker.core.cloud.domain.usecase

import com.example.financetracker.core.cloud.domain.repository.RemoteRepository

class InternetConnectionAvailability(
    private val remoteRepository: RemoteRepository,
) {
    suspend operator fun invoke(): Boolean{
        return remoteRepository.isInternetConnected()
    }
}