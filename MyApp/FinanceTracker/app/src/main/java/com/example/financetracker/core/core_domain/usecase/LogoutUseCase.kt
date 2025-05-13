package com.example.financetracker.core.core_domain.usecase

import com.example.financetracker.core.cloud.domain.repository.RemoteRepository
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    suspend operator fun invoke(){
        remoteRepository.logoutUser()
        sharedPreferencesRepository.setLoggedInState(false)
        sharedPreferencesRepository.removeUserIdLocally()
        sharedPreferencesRepository.removeUserNameLocally()
    }
}