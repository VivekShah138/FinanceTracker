package com.example.financetracker.auth_feature.domain.usecases

import com.example.financetracker.core.domain.repository.FirebaseRepository

class KeepUserLoggedIn(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke (keepLoggedIn: Boolean){
        return firebaseRepository.keepUserLoggedIn(keepLoggedIn)
    }
}