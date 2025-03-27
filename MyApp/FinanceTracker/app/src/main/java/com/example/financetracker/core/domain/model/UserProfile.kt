package com.example.financetracker.core.domain.model

import com.example.financetracker.setup_account.domain.model.Currency

data class UserProfile(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val baseCurrency: Map<String,Currency> = emptyMap(),
    val country: String = "",
    val callingCode: String = "",
    val phoneNumber: String = "",
    val profileSetUpCompleted: Boolean = false
)
