package com.example.financetracker.core.local.data.room.data_source.category.migration


import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val CATEGORY_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Step 1: Create a temporary table with the new schema
        db.execSQL("""
            CREATE TABLE category_temp(
                uid TEXT NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                type TEXT NOT NULL,
                icon TEXT NOT NULL,
                isCustom INTEGER NOT NULL
            )
        """)

        // Step 2: Copy data from the old table to the new table
        db.execSQL("""
            INSERT INTO category_temp(uid, name, type, icon, isCustom)
            SELECT 
                ROW_NUMBER() OVER (ORDER BY name) AS uid,   -- Generate a random UID for each category
                name, 
                type, 
                icon, 
                0 AS isCustom  -- Assuming predefined categories are not custom, set to 0 (false)
            FROM CategoryEntity
        """)

        // Step 3: Drop the old table
        db.execSQL("DROP TABLE CategoryEntity")

        // Step 4: Rename the new table to the old table's name
        db.execSQL("ALTER TABLE category_temp RENAME TO CategoryEntity")
    }
}


val CATEGORY_MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("DROP TABLE CategoryEntity")


        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS CategoryEntity (
                categoryId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                uid TEXT NOT NULL,
                name TEXT NOT NULL,
                type TEXT NOT NULL,
                icon TEXT NOT NULL,
                isCustom INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }
}

