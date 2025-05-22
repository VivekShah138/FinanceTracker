package com.example.financetracker.core.local.data.shared_preferences.repository

import androidx.compose.animation.core.rememberTransition
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

    override fun setDarkMode(isDarkMode: Boolean) {
        return userPreferences.setDarkMode(isDarkMode)
    }

    override fun getDarkMode(): Boolean {
        return userPreferences.getDarkMode()
    }

    override fun getUserName(): String? {
        return userPreferences.getUserName()
    }

    override fun setUserName(userName: String) {
        return userPreferences.setUserName(userName)
    }

    override fun removeUserNameLocally() {
        return userPreferences.removeUserNameLocally()
    }

    override fun isFirstTimeInstalled(): Boolean {
        return userPreferences.isFirstTimeInstalled()
    }

    override fun setFirstTimeInstalled() {
        return userPreferences.setFirstTimeInstalled()
    }

    override fun isFirstTimeLoggedIn(uid: String): Boolean {
        return userPreferences.isFirstTimeLoggedIn(uid)
    }

    override fun setFirstTimeLoggedIn(uid: String) {
        return userPreferences.setFirstTimeLoggedIn(uid)
    }


}