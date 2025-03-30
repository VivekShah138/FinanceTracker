package com.example.financetracker.core.local.data.room.data_source.userprofile.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val USER_PROFILE_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // Create a temporary table with the new schema
        db.execSQL("""
            CREATE TABLE user_profile_temp(
                uid TEXT NOT NULL PRIMARY KEY,
                firstName TEXT NOT NULL,
                lastName TEXT NOT NULL,
                email TEXT NOT NULL,
                baseCurrency TEXT,
                country TEXT NOT NULL,
                callingCode TEXT NOT NULL,
                phoneNumber TEXT NOT NULL,
                profileSetUpCompleted INTEGER NOT NULL
            )
        """)

        // Copy data from the old table to the new table
        // If `uid` is null, insert a random string for `uid`
        db.execSQL("""
            INSERT INTO user_profile_temp(uid, firstName, lastName, email, baseCurrency, country, callingCode, phoneNumber, profileSetUpCompleted)
            SELECT 
                email AS uid,  -- Use email as uid since `uid` column doesn't exist
                firstName, 
                lastName, 
                email, 
                baseCurrency, 
                country, 
                callingCode, 
                phoneNumber, 
                profileSetUpCompleted
            FROM UserProfileEntity
        """)

        // Drop the old table
        db.execSQL("DROP TABLE UserProfileEntity")

        // Rename the new table to the old table's name
        db.execSQL("ALTER TABLE user_profile_temp RENAME TO UserProfileEntity")
    }
}
