package com.example.financetracker.domain.usecases.local.shared_pref

import com.example.financetracker.domain.repository.local.SharedPreferencesRepository

class SetFirstTimeLogin(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    operator fun invoke(uid: String){
        return sharedPreferencesRepository.setFirstTimeLoggedIn(uid)
    }

}