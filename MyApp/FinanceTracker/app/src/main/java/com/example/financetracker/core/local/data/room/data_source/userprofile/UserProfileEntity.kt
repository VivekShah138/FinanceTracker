package com.example.financetracker.core.local.data.room.data_source.userprofile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.financetracker.setup_account.domain.model.Currency

@Entity
data class UserProfileEntity(

    val firstName: String ,
    val lastName: String ,
    @PrimaryKey
    val email: String,
    val baseCurrency: String?,
    val country: String ,
    val callingCode: String,
    val phoneNumber: String,
    val profileSetUpCompleted: Boolean
)
