package com.example.financetracker.presentation.features.auth_feature.states

import com.example.financetracker.domain.model.UserProfile

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
