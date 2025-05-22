package com.example.financetracker.core.local.data.shared_preferences.data_source

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object{
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val CURRENCY_RATES_UPDATED = "currency_rates_updated"
        private const val CLOUD_SYNC = "cloud_sync"
        private const val DARK_MODE = "dark_mode"
        private const val USER_NAME = "user_name"
        private const val FIRST_INSTALL = "first_install"
        private const val FIRST_LOGIN = "first_login"
    }

    fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false)
    }

    fun setLoggedInState(isLoggedIn: Boolean) {
        sharedPreferences.edit() {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        }
    }

    fun isFirstTimeInstalled(): Boolean{
        return sharedPreferences.getBoolean(FIRST_INSTALL,true)
    }

    fun setFirstTimeInstalled(){
        sharedPreferences.edit() {
            putBoolean(FIRST_INSTALL, false)
        }
    }

    fun isFirstTimeLoggedIn(uid: String): Boolean {
        return sharedPreferences.getBoolean("${FIRST_LOGIN}_$uid", true)
    }

    fun setFirstTimeLoggedIn(uid: String) {
        sharedPreferences.edit() {
            putBoolean("${FIRST_LOGIN}_$uid", false)
        }
    }


    fun getUserIdLocally(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun setUserIdLocally(userId: String) {
        sharedPreferences.edit {
            putString(KEY_USER_ID, userId)
        }
    }

    fun removeUserIdLocally(){
        sharedPreferences.edit(){
            remove(KEY_USER_ID)
        }
    }

    fun setCurrencyRatesUpdated(isUpdated: Boolean){
        sharedPreferences.edit(){
            putBoolean(CURRENCY_RATES_UPDATED,isUpdated)
        }
    }

    fun getCurrencyRatesUpdated(): Boolean{
        return sharedPreferences.getBoolean(CURRENCY_RATES_UPDATED,false)
    }

    fun setCloudSync(isSynced: Boolean){
        sharedPreferences.edit(){
            putBoolean(CLOUD_SYNC,isSynced)
        }
    }

    fun getCloudSync(): Boolean{
        return sharedPreferences.getBoolean(CLOUD_SYNC,false)
    }

    fun setDarkMode(isDarkMode: Boolean){
        sharedPreferences.edit(){
            putBoolean(DARK_MODE,isDarkMode)
        }
    }

    fun getDarkMode(): Boolean{
        return sharedPreferences.getBoolean(DARK_MODE,false)
    }

    fun setUserName(userName: String){
        sharedPreferences.edit(){
            putString(USER_NAME,userName)
        }
    }

    fun getUserName(): String?{
        return sharedPreferences.getString(USER_NAME,null)
    }

    fun removeUserNameLocally(){
        sharedPreferences.edit(){
            remove(USER_NAME)
        }
    }



    fun clearLogInPreference(){
        sharedPreferences.edit() {
            clear()
        }
    }

}