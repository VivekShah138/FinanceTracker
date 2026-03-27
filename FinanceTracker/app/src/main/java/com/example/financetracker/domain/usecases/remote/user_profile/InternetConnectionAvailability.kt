package com.example.financetracker.domain.usecases.remote.user_profile

import com.example.financetracker.domain.repository.remote.RemoteRepository

class InternetConnectionAvailability(
    private val remoteRepository: RemoteRepository,
) {
    operator fun invoke(): Boolean{
        return remoteRepository.isInternetConnected()
    }
}