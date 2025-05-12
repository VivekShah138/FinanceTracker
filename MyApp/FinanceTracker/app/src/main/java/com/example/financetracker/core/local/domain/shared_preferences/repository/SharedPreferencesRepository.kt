package com.example.financetracker.core.local.domain.shared_preferences.repository

interface SharedPreferencesRepository {
    fun checkIsLoggedIn(): Boolean
    fun setLoggedInState(keepLoggedIn: Boolean)
    fun getUserIdLocally(): String?
    fun setUserIdLocally(userId: String)
    fun removeUserIdLocally()
    fun setCurrencyRatesUpdated(isUpdated: Boolean)
    fun getCurrencyRatesUpdated(): Boolean
    fun setCloudSync(isSynced: Boolean)
    fun getCloudSync(): Boolean
    fun setDarkMode(isDarkMode: Boolean)
    fun getDarkMode(): Boolean
}