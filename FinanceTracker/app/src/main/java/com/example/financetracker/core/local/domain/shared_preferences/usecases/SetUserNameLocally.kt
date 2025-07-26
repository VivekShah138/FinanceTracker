package com.example.financetracker.core.local.domain.shared_preferences.usecases

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetUserNameLocally(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(userName: String){
        sharedPreferencesRepository.setUserName(userName)
    }

}