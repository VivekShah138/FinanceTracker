package com.example.financetracker.core.cloud.domain.usecase

import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import javax.inject.Inject

class GetUserEmailUserCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(): String?{
        return remoteRepository.getCurrentUserEmail()
    }
}