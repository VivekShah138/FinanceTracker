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
    }

    fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false)
    }

    fun setLoggedInState(isLoggedIn: Boolean) {
        sharedPreferences.edit() {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
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

    fun clearLogInPreference(){
        sharedPreferences.edit() {
            clear()
        }
    }

}