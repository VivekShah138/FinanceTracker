package com.example.financetracker.core.cloud.domain.usecase

import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetUserUIDUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(): String?{
        return firebaseRepository.getCurrentUserID()
    }
}