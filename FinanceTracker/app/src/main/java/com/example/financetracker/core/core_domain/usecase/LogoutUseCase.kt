package com.example.financetracker.core.core_domain.usecase

import com.example.financetracker.domain.repository.remote.RemoteRepository
import com.example.financetracker.domain.repository.local.SharedPreferencesRepository
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