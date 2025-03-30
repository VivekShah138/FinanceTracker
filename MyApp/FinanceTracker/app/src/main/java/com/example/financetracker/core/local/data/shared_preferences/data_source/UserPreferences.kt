package com.example.financetracker.core.local.data.shared_preferences.data_source

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object{
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false)
    }

    fun setLoggedInState(isLoggedIn: Boolean) {
        sharedPreferences.edit() {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        }
    }

    fun clearLogInPreference(){
        sharedPreferences.edit() {
            clear()
        }
    }

}