package com.example.financetracker.core.local.domain.shared_preferences.repository

interface SharedPreferencesRepository {
    fun checkIsLoggedIn(): Boolean
    fun setLoggedInState(keepLoggedIn: Boolean)
}