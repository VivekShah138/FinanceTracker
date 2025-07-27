package com.example.financetracker.data.data_source.local.room.modules.userprofile

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [com.example.financetracker.data.data_source.local.room.modules.userprofile.UserProfileEntity::class],
    version = 2
)
abstract class UserProfileDatabase:RoomDatabase() {

    abstract val userProfileDao: UserProfileDao
    companion object{
        const val DATABASE_NAME = "user_profile_db"
    }
}