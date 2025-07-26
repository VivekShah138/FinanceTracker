package com.example.financetracker.data.local.data_source.room.modules.userprofile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.financetracker.setup_account.domain.model.Currency

@Entity
data class UserProfileEntity(

    @PrimaryKey
    val uid: String,
    val firstName: String ,
    val lastName: String ,
    val email: String,
    val baseCurrency: String?,
    val country: String ,
    val callingCode: String,
    val phoneNumber: String,
    val profileSetUpCompleted: Boolean
)
