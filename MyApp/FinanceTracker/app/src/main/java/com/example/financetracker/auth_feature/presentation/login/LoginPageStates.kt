package com.example.financetracker.auth_feature.presentation.login

import com.example.financetracker.core.domain.model.UserProfile

data class LoginPageStates(
    val loggedInUser: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val password: String = "",
    val errorMessage: String? = null,
    val keepLoggedIn: Boolean = false,
    val userProfile: UserProfile = UserProfile()
)
