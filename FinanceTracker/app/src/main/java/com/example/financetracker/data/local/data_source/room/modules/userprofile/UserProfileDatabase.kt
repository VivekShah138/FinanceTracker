package com.example.financetracker.data.local.data_source.room.modules.userprofile

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserProfileEntity::class],
    version = 2
)
abstract class UserProfileDatabase:RoomDatabase() {

    abstract val userProfileDao: UserProfileDao
    companion object{
        const val DATABASE_NAME = "user_profile_db"
    }
}