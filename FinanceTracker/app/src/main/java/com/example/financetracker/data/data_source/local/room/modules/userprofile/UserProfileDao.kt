package com.example.financetracker.data.data_source.local.room.modules.userprofile

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM UserProfileEntity where uid = :uid")
    fun getUserProfile(uid: String): UserProfileEntity

    @Upsert
    suspend fun insertUserProfile(userProfileEntity: UserProfileEntity)
}