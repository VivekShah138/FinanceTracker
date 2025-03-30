package com.example.financetracker.core.local.data.room.data_source.userprofile

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.financetracker.core.local.data.room.data_source.category.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM UserProfileEntity where uid = :uid")
    fun getUserProfile(uid: String): UserProfileEntity

    @Upsert
    suspend fun insertUserProfile(userProfileEntity: UserProfileEntity)

}