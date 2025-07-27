package com.example.financetracker.domain.usecases.remote.user_profile

import com.example.financetracker.domain.repository.remote.RemoteRepository
import javax.inject.Inject

class GetUserUIDUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(): String?{
        return remoteRepository.getCurrentUserID()
    }
}