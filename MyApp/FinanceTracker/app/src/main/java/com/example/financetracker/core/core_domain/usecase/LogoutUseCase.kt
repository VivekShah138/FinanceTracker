package com.example.financetracker.core.core_domain.usecase

import com.example.financetracker.core.cloud.domain.repository.FirebaseRepository
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    suspend operator fun invoke(){
        firebaseRepository.logoutUser()
        sharedPreferencesRepository.setLoggedInState(false)
    }
}