package com.example.financetracker.data.data_source.local.room.modules.userprofile

import androidx.room.Entity
import androidx.room.PrimaryKey

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
