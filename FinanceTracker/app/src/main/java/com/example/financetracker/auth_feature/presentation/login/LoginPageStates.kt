package com.example.financetracker.auth_feature.presentation.login

import com.example.financetracker.core.local.domain.room.model.UserProfile

data class LoginPageStates(
    val loggedInUser: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val password: String = "",
    val errorMessage: String? = null,
    val userProfile: UserProfile = UserProfile(),
    val isLoading: Boolean = false,
    val isDataSyncing: Boolean = false
)
