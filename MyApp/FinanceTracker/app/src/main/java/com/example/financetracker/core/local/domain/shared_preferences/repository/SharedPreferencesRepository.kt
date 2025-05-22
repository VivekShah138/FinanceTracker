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
    fun getUserName(): String?
    fun setUserName(userName: String)
    fun removeUserNameLocally()
    fun isFirstTimeInstalled(): Boolean
    fun setFirstTimeInstalled()
    fun isFirstTimeLoggedIn(uid: String): Boolean
    fun setFirstTimeLoggedIn(uid: String)
}