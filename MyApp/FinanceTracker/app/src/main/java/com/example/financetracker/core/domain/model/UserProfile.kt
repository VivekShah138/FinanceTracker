package com.example.financetracker.core.domain.model

data class UserProfile(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val baseCurrency: String = "",
    val country: String = "",
    val callingCode: String = "",
    val phoneNumber: String = "",
    val profileSetUpCompleted: Boolean = false
)
