package com.example.financetracker.core.local.data.room.data_source.userprofile

import androidx.room.PrimaryKey
import com.example.financetracker.setup_account.domain.model.Currency

data class UserProfileEntity(
    val firstName: String ,
    val lastName: String ,
    @PrimaryKey
    val email: String,
    val baseCurrency: Map<String, Currency> = emptyMap(),
    val country: String = "",
    val callingCode: String = "",
    val phoneNumber: String = "",
    val profileSetUpCompleted: Boolean = false
)
