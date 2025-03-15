package com.example.financetracker.core.domain.usecases

import com.example.financetracker.core.domain.repository.FirebaseRepository

class CheckIsLoggedInUseCase(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(): Boolean{
        return firebaseRepository.checkIsLoggedIn()
    }
}