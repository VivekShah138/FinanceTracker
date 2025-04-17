package com.example.financetracker.core.local.data.shared_preferences.repository

import com.example.financetracker.core.local.data.shared_preferences.data_source.UserPreferences
import com.example.financetracker.core.local.domain.shared_preferences.repository.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences
): SharedPreferencesRepository {

    override fun checkIsLoggedIn(): Boolean {
        return userPreferences.isLoggedIn()
    }

    override fun setLoggedInState(keepLoggedIn: Boolean) {
        return userPreferences.setLoggedInState(keepLoggedIn)
    }

    override fun getUserIdLocally(): String? {
        return userPreferences.getUserIdLocally()
    }

    override fun setUserIdLocally(userId: String) {
        userPreferences.setUserIdLocally(userId)
    }

    override fun removeUserIdLocally() {
        userPreferences.removeUserIdLocally()
    }

    override fun setCurrencyRatesUpdated(isUpdated: Boolean) {
        userPreferences.setCurrencyRatesUpdated(isUpdated)
    }

    override fun getCurrencyRatesUpdated(): Boolean {
        return userPreferences.getCurrencyRatesUpdated()
    }

    override fun setCloudSync(isSynced: Boolean) {
        return userPreferences.setCloudSync(isSynced)
    }

    override fun getCloudSync(): Boolean {
        return userPreferences.getCloudSync()
    }
}