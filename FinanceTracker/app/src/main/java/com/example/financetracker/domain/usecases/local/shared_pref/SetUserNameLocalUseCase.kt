package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetUserNameLocalUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(userName: String){
        sharedPreferencesRepository.setUserName(userName)
    }

}